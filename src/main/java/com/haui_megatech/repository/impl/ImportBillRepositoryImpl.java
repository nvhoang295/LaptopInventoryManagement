/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.haui_megatech.repository.impl;

import com.haui_megatech.ApplicationContext;
import com.haui_megatech.model.ImportBill;
import com.haui_megatech.repository.ImportBillRepository;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

/**
 *
 * @author vieth
 */
public class ImportBillRepositoryImpl implements ImportBillRepository {
    
    private final String ABS_DATA_PATH;
    
    public ImportBillRepositoryImpl() {
        ABS_DATA_PATH = new ApplicationContext().ABS_IMPORT_BILLS_DATA_PATH;
        
        initCounter();
    }
    
    private void initCounter() {
        ArrayList<ImportBill> importBills = this.getAll();
        if (importBills.isEmpty()) {
            ImportBill.counter = 0;
        } else {
            ImportBill.counter = importBills.getLast().getId();
        }
    }
    
    private boolean saveToDisk(ArrayList<ImportBill> list) {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(ABS_DATA_PATH)
        )) {
            oos.writeObject(list);
            return true;
        } catch (IOException e) {
            return false;
        }
    }
    
    @Override
    public Optional<ImportBill> findById(Integer id) {
        return this.getAll()
                .parallelStream()
                .filter(item -> item.getId().equals(id))
                .findFirst();
    }

    @Override
    public Optional<ImportBill> save(ImportBill importBill) {
        ArrayList<ImportBill> importBills = this.getAll();
        if (importBill.getId() != null) {
            int foundIndex = this.findIndexById(importBill.getId());
            ImportBill foundImportBill = importBills.get(foundIndex);
            update(foundImportBill, importBill);
            importBills.set(foundIndex, foundImportBill);
            return this.saveToDisk(importBills)
                    ? Optional.of(foundImportBill)
                    : Optional.empty();
        }
        
        importBill.setId(++ImportBill.counter);
        importBill.setWhenCreated(new Date());
        importBills.add(importBill);
        
        return this.saveToDisk(importBills)
                ? Optional.of(importBill)
                : Optional.empty();
    }
    
    private int findIndexById(Integer id) {
        ArrayList<ImportBill> importBills = this.getAll();
        for (int i = 0; i < importBills.size(); ++i) {
            if (importBills.get(i).getId().equals(id)) return i;
        }
        return -1;
    }
    
    private boolean update(ImportBill oldInfo, ImportBill newInfo) {
        oldInfo.setTotal(newInfo.getTotal());
        return true;
    }

    @Override
    public ArrayList<ImportBill> saveAll(ArrayList<ImportBill> importBills) {
        ArrayList<ImportBill> savedImportBills = new ArrayList<>();
        importBills.forEach(item -> {
            Optional<ImportBill> saved = this.save(item);
            if (saved.isPresent()) {
                savedImportBills.add(saved.get());
            }
        });
        return savedImportBills;
    }

    @Override
    public void deleteById(int id) {
        ArrayList<ImportBill> importBills = this.getAll();
        importBills.removeIf(item -> item.getId().equals(id));
        this.saveToDisk(importBills);
    }

    @Override
    public ArrayList<ImportBill> getAll() {
        ArrayList<ImportBill> importBills;
        try (ObjectInputStream ois = new ObjectInputStream(
                (new FileInputStream(ABS_DATA_PATH))
        )) {
            importBills = (ArrayList<ImportBill>) ois.readObject();
            if (importBills == null) {
                importBills = new ArrayList<>();
            }
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList();
        }
        return importBills;
    }
    

}
