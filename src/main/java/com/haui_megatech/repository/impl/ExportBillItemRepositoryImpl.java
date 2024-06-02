/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.haui_megatech.repository.impl;

import com.haui_megatech.ApplicationContext;
import com.haui_megatech.model.ExportBillItem;
import com.haui_megatech.repository.ExportBillItemRepository;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import com.haui_megatech.repository.ImportBillItemRepository;

/**
 *
 * @author vieth
 */
public class ExportBillItemRepositoryImpl implements ExportBillItemRepository {
    
    private final ApplicationContext applicationContext;
    
    private final String ABS_DATA_PATH;
    
    public ExportBillItemRepositoryImpl(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        
        ABS_DATA_PATH = this.applicationContext.ABS_EXPORT_BILL_ITEMS_DATA_PATH;
        
        initCounter();
    }
    
    private void initCounter() {
        ArrayList<ExportBillItem> items = this.getAll();
        if (items.isEmpty()) {
            ExportBillItem.counter = 0;
        } else {
            ExportBillItem.counter = items.getLast().getId();
        }
    }
    
    private boolean saveToDisk(ArrayList<ExportBillItem> list) {
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
    public Optional<ExportBillItem> findById(Integer id) {
        return this.getAll()
                .parallelStream()
                .filter(item -> item.getId().equals(id))
                .findFirst();
    }

    @Override
    public Optional<ExportBillItem> save(ExportBillItem item) {
        ArrayList<ExportBillItem> items = this.getAll();
        if (item.getId() != null) {
            int foundIndex = this.findIndexById(item.getId());
            ExportBillItem foundItem = items.get(foundIndex);
            update(foundItem, item);
            items.set(foundIndex, foundItem);
            return this.saveToDisk(items)
                    ? Optional.of(foundItem)
                    : Optional.empty();
        }

        item.setId(++ExportBillItem.counter);
        items.add(item);

        return this.saveToDisk(items)
                ? Optional.of(item)
                : Optional.empty();
    }
    
    private int findIndexById(Integer id) {
        ArrayList<ExportBillItem> items = this.getAll();
        for (int i = 0; i < items.size(); ++i) {
            if (items.get(i).getId().equals(id)) return i;
        }
        return -1;
    }
    
    private boolean update(ExportBillItem oldInfo, ExportBillItem newInfo) {
        oldInfo.setQuantity(newInfo.getQuantity());
        return true;
    }

    @Override
    public ArrayList<ExportBillItem> saveAll(ArrayList<ExportBillItem> items) {
        ArrayList<ExportBillItem> savedItems = new ArrayList<>();
        items.forEach(item -> {
            Optional<ExportBillItem> savedItem = this.save(item);
            if (savedItem.isPresent()) {
                savedItems.add(savedItem.get());
            }
        });
        return savedItems;
    }

    @Override
    public void deleteById(int id) {
        ArrayList<ExportBillItem> items = this.getAll();
        items.removeIf(item -> item.getId().equals(id));
        this.saveToDisk(items);
    }

    @Override
    public ArrayList<ExportBillItem> getAll() {
        ArrayList<ExportBillItem> products;
        try (ObjectInputStream ois = new ObjectInputStream(
                (new FileInputStream(ABS_DATA_PATH))
        )) {
            products = (ArrayList<ExportBillItem>) ois.readObject();
            if (products == null) {
                products = new ArrayList<>();
            }
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList();
        }
        return products;
    }
    
}
