package com.warehause.warehause1.service;

import com.warehause.warehause1.exceptionHandler.NotFoundException;
import com.warehause.warehause1.model.Client;
import com.warehause.warehause1.model.Device;
import com.warehause.warehause1.model.Importer;
import com.warehause.warehause1.model.Seller;
import com.warehause.warehause1.repository.ImporterJpaRepository;
import com.warehause.warehause1.repository.SellerJpaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class ImporterServiceTest {
    @Mock
    private ImporterJpaRepository importerJpaRepository;
    @Mock
    private SellerJpaRepository sellerJpaRepository;
    @InjectMocks
    private ImporterService importerService;
    @Mock
    private SellerService sellerService;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void getAllImporters() {
        Importer importer1 = new Importer(1, "Ablo", "Bablo");
        Importer importer2 = new Importer(2, "Anto", "Banto");
        Importer importer3 = new Importer(1, "Arte", "Barte");
        List<Importer> checkImporters = List.of(importer1, importer2, importer3);
        when(importerJpaRepository.findAll()).thenReturn(checkImporters);
        List<Importer> myImporters = (List<Importer>) importerService.getAllImporters();
        assertEquals(checkImporters, myImporters);
    }

    @Test
    void getImporterByIdShouldReturnImporterById() {
        Importer importer1 = new Importer(1, "Ablo", "Bablo");
        when(importerJpaRepository.findById(anyInt())).thenReturn(Optional.of(importer1));
        Optional<Importer> retrieveImporter = Optional.ofNullable(importerService.getImporterById(1));
        Assertions.assertTrue(retrieveImporter.isPresent());
        assertEquals(importer1, retrieveImporter.get());
    }
    @Test
    void getImporterByIdShouldReturnNullIfImporterDoesntExist(){
        NotFoundException thrown = Assertions.assertThrows(NotFoundException.class, () -> {
            int importerToFindById = 1000;
            when(importerJpaRepository.findById(importerToFindById)).thenReturn(Optional.empty());
            importerService.getImporterById(importerToFindById);
        });
        Assertions.assertEquals("Nie ma takiego importera", thrown.getMessage());
    }
    @Test
    void saveNewShouldAddAndReturnNewImporter() {
        Importer importer1 = new Importer(1, "Ablo", "Bablo");
        when(importerJpaRepository.save(importer1)).thenReturn(importer1);
        Importer saveImporter = this.importerService.saveNew(importer1);
        assertEquals(saveImporter, importer1);
    }
    @Test
    void saveNewShouldANotSaveExistingImporter(){
        NotFoundException thrown = Assertions.assertThrows(NotFoundException.class, () -> {
            Importer importer1 = new Importer(1, "Ablo", "Bablo");
            when(importerJpaRepository.existsById(importer1.getImporterId())).thenReturn(true);
            this.importerService.saveNew(importer1);
        });
        assertEquals("Importer o podanym Id już istnieje", thrown.getMessage());
    }

    @Test
    void deleteImporterByIdShouldDeleteImporter() {
        Integer importerId = 1;
        Importer importer = new Importer(importerId,"Ablo", "Bablo");
        when(importerJpaRepository.findById(anyInt())).thenReturn(Optional.of(importer));
        importerService.deleteImporterById(importerId);
        verify(importerJpaRepository, times(1)).deleteById(importerId);
    }
    @Test
    void deleteImporterByIdShouldNotDeleteImporter() {
        NotFoundException thrown = Assertions.assertThrows(NotFoundException.class, () -> {
            Integer importerIdToDelete = 300;
            when(importerJpaRepository.existsById(importerIdToDelete)).thenReturn(false);
            importerService.deleteImporterById(importerIdToDelete);
        });
        assertEquals("Nie ma takiego importera", thrown.getMessage());
    }

    @Test
    void fulImporterUpdateShouldUpdatedImporter() {
        Integer importerId = 1;
        Importer existingImporter = new Importer(1, "Ablo", "Bablo");
        existingImporter.setImporterId(importerId);
        Importer updatedImporter = new Importer();
        updatedImporter.setImporterId(importerId);
        updatedImporter.setImporterName("AbloUpdated");
        updatedImporter.setImporterProducent("BabloUpdated");
        when(importerJpaRepository.existsById(importerId)).thenReturn(true);
        when(importerJpaRepository.save(updatedImporter)).thenReturn(updatedImporter);
        Importer result = importerService.fullImporterUpdate(importerId, updatedImporter);
        verify(importerJpaRepository, times(1)).save(updatedImporter);
    }
    @Test
    void fulImporterUpdateShouldNotUpdateImporterWhenDoesntEsxist() {
        NotFoundException thrown = assertThrows(NotFoundException.class, () -> {
            Integer importerId = 1;
            Importer updatedImporter = new Importer();
            updatedImporter.setImporterId(300);
            when(importerJpaRepository.existsById(importerId)).thenReturn(false);
            Importer result = importerService.fullImporterUpdate(importerId, updatedImporter);
        });
        assertEquals("Nie ma takiego importera", thrown.getMessage());
    }
    @Test
    void setSellerForImporter() {
        Integer importerId = 1;
        Integer sellerId = 1;
        Importer importer = new Importer(importerId, "Ablo", "Bablo");
        Seller seller = new Seller(sellerId, "Adam Adamski", "junior", 34);
        when(importerJpaRepository.existsById(anyInt())).thenReturn(true);
        when(sellerJpaRepository.existsById(anyInt())).thenReturn(true);
        when(importerJpaRepository.findById(anyInt())).thenReturn(Optional.of(importer));
        when(sellerJpaRepository.findById(anyInt())).thenReturn(Optional.of(seller));
        when(importerJpaRepository.save(importer)).thenReturn(importer);
        ImporterService importerService = new ImporterService(importerJpaRepository, sellerJpaRepository);
        Optional<Importer> result = importerService.setSellerForImporter(sellerId, importerId);
        assertTrue(result.isPresent());
        assertEquals(importer, result.get());
        verify(importerJpaRepository, times(1)).existsById(importerId);
        verify(sellerJpaRepository, times(1)).existsById(sellerId);
        verify(importerJpaRepository, times(1)).findById(importerId);
        verify(sellerJpaRepository, times(1)).findById(sellerId);
        verify(importerJpaRepository, times(1)).save(importer);
    }
    @Test
    void findByImporterNameShouldFindImporterByName() {
        String importerName = "Ablo";
        Importer importer1 = new Importer(1, "Ablo", "Bablo");
        Importer importer2 = new Importer(2, "Abrgo", "Bargo");
        Importer importer3 = new Importer(3, "Gargo", "Margo");
        when(importerJpaRepository.findByImporterNameContaining(importerName))
                .thenReturn(Arrays.asList(importer1, importer2, importer3));
        List<Importer> result = importerService.findByImporterName(importerName);
        assertEquals(3, result.size());
        assertTrue(result.contains(importer1));
        assertTrue(result.contains(importer2));
        assertTrue(result.contains(importer3));
        verify(importerJpaRepository, times(1)).findByImporterNameContaining(importerName);
    }

}