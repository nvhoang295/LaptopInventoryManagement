/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.haui_megatech.repository.impl;

import com.haui_megatech.ApplicationContext;
import com.haui_megatech.model.Product;
import com.haui_megatech.repository.ProductRepository;
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
public class ProductRepositoryImpl implements ProductRepository {

    private final String ABS_DATA_PATH;

    public ProductRepositoryImpl() {
        ABS_DATA_PATH = new ApplicationContext().ABS_PRODUCTS_DATA_PATH;

        initCounter();
    }

    private void initCounter() {
        ArrayList<Product> products = this.getAll();
        if (products.isEmpty()) {
            Product.counter = 0;
        } else {
            Product.counter = products.getLast().getId();
        }
    }

    private boolean saveToDisk(ArrayList<Product> list) {
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
    public Optional<Product> findById(Integer id) {
        return this.getAll()
                .parallelStream()
                .filter(item -> item.getId().equals(id))
                .findFirst();
    }

    @Override
    public Optional<Product> save(Product product) {
        ArrayList<Product> products = this.getAll();
        if (product.getId() != null) {
            int foundIndex = this.findIndexById(product.getId());
            Product foundProduct = products.get(foundIndex);
            update(foundProduct, product);
            products.set(foundIndex, foundProduct);
            return this.saveToDisk(products)
                    ? Optional.of(foundProduct)
                    : Optional.empty();
        }

        product.setId(++Product.counter);
        product.setWhenCreated(new Date());
        product.setImportBillItems(new ArrayList<>());
        product.setExportBillItems(new ArrayList<>());
        products.add(product);

        return this.saveToDisk(products)
                ? Optional.of(product)
                : Optional.empty();
    }

    private int findIndexById(Integer id) {
        ArrayList<Product> products = this.getAll();
        for (int i = 0; i < products.size(); ++i) {
            if (products.get(i).getId().equals(id)) {
                return i;
            }
        }
        return -1;
    }

    private boolean update(Product oldInfo, Product newInfo) {
        oldInfo.setName(newInfo.getName());
        oldInfo.setProcessor(newInfo.getProcessor());
        oldInfo.setMemory(newInfo.getMemory());
        oldInfo.setStorage(newInfo.getStorage());
        oldInfo.setDisplay(newInfo.getDisplay());
        oldInfo.setBattery(newInfo.getBattery());
        oldInfo.setCard(newInfo.getCard());
        oldInfo.setWeight(newInfo.getWeight());
        oldInfo.setLastUpdated(newInfo.getLastUpdated());
        return true;
    }

    @Override
    public ArrayList<Product> saveAll(ArrayList<Product> products) {
        ArrayList<Product> savedProducts = new ArrayList<>();
        products.forEach(item -> {
            Optional<Product> saved = this.save(item);
            if (saved.isPresent()) {
                savedProducts.add(saved.get());
            }
        });
        return savedProducts;
    }

    @Override
    public void deleteById(int id) {
        ArrayList<Product> products = this.getAll();
        products.removeIf(item -> item.getId().equals(id));
        this.saveToDisk(products);
    }

    @Override
    public ArrayList<Product> getAll() {
        ArrayList<Product> products;
        try (ObjectInputStream ois = new ObjectInputStream(
                (new FileInputStream(ABS_DATA_PATH))
        )) {
            products = (ArrayList<Product>) ois.readObject();
            if (products == null) {
                products = new ArrayList<>();
            }
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList();
        }
        return products;
    }

}
