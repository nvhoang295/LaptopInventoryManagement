/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.haui_megatech.repository.impl;

import com.haui_megatech.ApplicationContext;
import com.haui_megatech.model.ExportBill;
import com.haui_megatech.repository.ExportBillRepository;
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
public class ExportBillRepositoryImpl implements ExportBillRepository {
    
    private final ApplicationContext applicationContext;
    
    private final String ABS_DATA_PATH;
    
    public ExportBillRepositoryImpl(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        
        ABS_DATA_PATH = this.applicationContext.ABS_EXPORT_BILLS_DATA_PATH;
        
        initCounter();
    }
    
    private void initCounter() {
        ArrayList<ExportBill> items = this.getAll();
        if (items.isEmpty()) {
            ExportBill.counter = 0;
        } else {
            ExportBill.counter = items.getLast().getId();
        }
    }
    
    private boolean saveToDisk(ArrayList<ExportBill> list) {
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
    public Optional<ExportBill> findById(Integer id) {
        return this.getAll()
                .parallelStream()
                .filter(item -> item.getId().equals(id))
                .findFirst();
    }

    @Override
    public Optional<ExportBill> save(ExportBill item) {
        ArrayList<ExportBill> items = this.getAll();
        if (item.getId() != null) {
            int foundIndex = this.findIndexById(item.getId());
            ExportBill found = items.get(foundIndex);
            update(found, item);
            items.set(foundIndex, found);
            return this.saveToDisk(items)
                    ? Optional.of(found)
                    : Optional.empty();
        }
        
        item.setId(++ExportBill.counter);
        item.setWhenCreated(new Date());
        items.add(item);
        
        return this.saveToDisk(items)
                ? Optional.of(item)
                : Optional.empty();
    }
    
    private int findIndexById(Integer id) {
        ArrayList<ExportBill> items = this.getAll();
        for (int i = 0; i < items.size(); ++i) {
            if (items.get(i).getId().equals(id)) return i;
        }
        return -1;
    }
    
    private boolean update(ExportBill oldInfo, ExportBill newInfo) {
        oldInfo.setClientName(newInfo.getClientName());
        oldInfo.setClientPhoneNumber(newInfo.getClientPhoneNumber());
        oldInfo.setClientAddress(newInfo.getClientAddress());
        oldInfo.setLastUpdated(new Date());
        return true;
    }

    @Override
    public void deleteById(int id) {
        ArrayList<ExportBill> items = this.getAll();
        items.removeIf(item -> item.getId().equals(id));
        this.saveToDisk(items);
    }

    @Override
    public ArrayList<ExportBill> getAll() {
        ArrayList<ExportBill> items;
        try (ObjectInputStream ois = new ObjectInputStream(
                (new FileInputStream(ABS_DATA_PATH))
        )) {
            items = (ArrayList<ExportBill>) ois.readObject();
            if (items == null) {
                items = new ArrayList<>();
            }
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList();
        }
        return items;
    }
    
}
