/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.haui_megatech.repository.impl;

import com.haui_megatech.ApplicationContext;
import com.haui_megatech.model.ImportBillItem;
import com.haui_megatech.model.InventoryItem;
import com.haui_megatech.repository.InventoryItemRepository;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

/**
 *
 * @author vieth
 */
public class InventoryItemRepositoryImpl implements InventoryItemRepository {
    
    private final ApplicationContext applicationContext;
    
    private final String ABS_DATA_PATH;
    
    public InventoryItemRepositoryImpl(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        
        ABS_DATA_PATH = applicationContext.ABS_INVENTORY_ITEMS_DATA_PATH;
        
        initCounter();
    }
    
    private void initCounter() {
        ArrayList<InventoryItem> items = this.getAll();
        if (items.isEmpty()) {
            InventoryItem.counter = 0;
        } else {
            InventoryItem.counter = items.getLast().getId();
        }
    }
    
    private boolean saveToDisk(ArrayList<InventoryItem> list) {
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
    public Optional<InventoryItem> findById(Integer id) {
        return this.getAll()
                .parallelStream()
                .filter(item -> item.getId().equals(id))
                .findFirst();
    }

    @Override
    public Optional<InventoryItem> save(InventoryItem item) {
        ArrayList<InventoryItem> items = this.getAll();
        if (item.getId() != null) {
            int foundIndex = this.findIndexById(item.getId());
            InventoryItem foundItem = items.get(foundIndex);
            update(foundItem, item);
            items.set(foundIndex, foundItem);
            return this.saveToDisk(items)
                    ? Optional.of(foundItem)
                    : Optional.empty();
        }

        item.setId(++InventoryItem.counter);
        items.add(item);

        return this.saveToDisk(items)
                ? Optional.of(item)
                : Optional.empty();
    }
    
    private int findIndexById(Integer id) {
        ArrayList<InventoryItem> items = this.getAll();
        for (int i = 0; i < items.size(); ++i) {
            if (items.get(i).getId().equals(id)) return i;
        }
        return -1;
    }
    
    private boolean update(InventoryItem oldInfo, InventoryItem newInfo) {
        oldInfo.setQuantity(newInfo.getQuantity());
        return true;
    }

    @Override
    public ArrayList<InventoryItem> saveAll(ArrayList<InventoryItem> items) {
        ArrayList<InventoryItem> savedItems = new ArrayList<>();
        items.forEach(item -> {
            Optional<InventoryItem> savedItem = this.save(item);
            if (savedItem.isPresent()) {
                savedItems.add(savedItem.get());
            }
        });
        return savedItems;
    }

    @Override
    public void deleteById(int id) {
        ArrayList<InventoryItem> items = this.getAll();
        items.removeIf(item -> item.getId().equals(id));
        this.saveToDisk(items);
    }

    @Override
    public ArrayList<InventoryItem> getAll() {
        ArrayList<InventoryItem> items;
        try (ObjectInputStream ois = new ObjectInputStream(
                (new FileInputStream(ABS_DATA_PATH))
        )) {
            items = (ArrayList<InventoryItem>) ois.readObject();
            if (items == null) {
                items = new ArrayList<>();
            }
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList();
        }
        return items;
    }
    
}
