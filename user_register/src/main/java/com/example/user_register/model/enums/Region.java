package com.example.user_register.model.enums;

public enum Region {
    AUVERGNE_RHONE_ALPES("Auvergne-Rhône-Alpes"),
    BOURGOGNE_FRANCHE_COMTE("Bourgogne-Franche-comté"),
    BRETAGNE("Bretagne"),
    CENTRE_VAL_DE_LOIRE("Centre-val de Loire"),
    CORSE("Corse"),
    GRAND_EST("Grand est"),
    HAUT_DE_FRANCE("Haut-de-France"),
    ILE_DE_FRANCE("Île-de-France"),
    NORMANDIE("Normandie"),
    NOUVELLE_AQUITAINE("Nouvelle-Aquitaine"),
    OCCITANIE("Occiatnie"),
    PAYS_DE_LA_LOIRE("Pays de la Loire"),
    PROVENCE_ALPES_COTE_D_AZUR("Provence-Alpes-Côte d'Azur");


    public final String region;

    Region(String region) {
        this.region = region;
    }
}
