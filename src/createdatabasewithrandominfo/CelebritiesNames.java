/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package createdatabasewithrandominfo;

/**
 *
 * @author blj0011
 */
public class CelebritiesNames {
    private String name;
    private String imageName;

    public CelebritiesNames(String name, String imageName) {
        this.name = name;
        this.imageName = imageName;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "CelebritiesNames{" + "name=" + name + ", imageName=" + imageName + '}';
    }
}
