/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package btl;

/**
 *
 * @author ASUS
 */
public class PROCESSOR {
    private int processorID;
    private String name;
    private String cores;
    private String threads;
    private double baseFrequency;
    private double booseFrequency;
    private String totalCache;
    private String integratedGraphicsCard;
    
    public PROCESSOR(){};

    public PROCESSOR(int processorID, String name, String cores, String threads, double baseFrequency, double booseFrequency, String totalCache, String integratedGraphicsCard) {
        this.processorID = processorID;
        this.name = name;
        this.cores = cores;
        this.threads = threads;
        this.baseFrequency = baseFrequency;
        this.booseFrequency = booseFrequency;
        this.totalCache = totalCache;
        this.integratedGraphicsCard = integratedGraphicsCard;
    }

    public int getProcessorID() {
        return processorID;
    }

    public void setProcessorID(int processorID) {
        this.processorID = processorID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCores() {
        return cores;
    }

    public void setCores(String cores) {
        this.cores = cores;
    }

    public String getThreads() {
        return threads;
    }

    public void setThreads(String threads) {
        this.threads = threads;
    }

    public double getBaseFrequency() {
        return baseFrequency;
    }

    public void setBaseFrequency(double baseFrequency) {
        this.baseFrequency = baseFrequency;
    }

    public double getBooseFrequency() {
        return booseFrequency;
    }

    public void setBooseFrequency(double booseFrequency) {
        this.booseFrequency = booseFrequency;
    }

    public String getTotalCache() {
        return totalCache;
    }

    public void setTotalCache(String totalCache) {
        this.totalCache = totalCache;
    }

    public String getIntegratedGraphicsCard() {
        return integratedGraphicsCard;
    }

    public void setIntegratedGraphicsCard(String integratedGraphicsCard) {
        this.integratedGraphicsCard = integratedGraphicsCard;
    }
    

   
    
    
    
}
