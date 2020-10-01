package com.diabetespaivakirja;

import java.util.ArrayList;
import java.util.List;

public class Verensokerit {
    private List<Verensokeri> verensokeriList;

    private static final Verensokerit verensokerit = new Verensokerit();

    private Verensokerit() {

        verensokeriList = new ArrayList<>();
    }

    public static Verensokerit getInstance() {
        return verensokerit;
    }

    public List<Verensokeri> getVerensokerit() {
        return verensokeriList;
    }

    public void setVerensokerit(List<Verensokeri> verensokerit) {
        verensokeriList = verensokerit;
    }

    public void lisaa(Verensokeri verensokeri) {
        for(int i = 0; i < verensokeriList.size(); i++) {
            if(verensokeriList.get(i).getVerensokeriID() == verensokeri.getVerensokeriID()) {
                //On jo olemassa verensokeri tälle ajalle.
                verensokeriList.remove(i);
                break;
            }
        }
        verensokeriList.add(verensokeri);
    }
}
