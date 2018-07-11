package skrypcior.atena.pl.kompilat;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;


public class Kompilat {
    
        private final SimpleIntegerProperty  id;
        private final SimpleStringProperty  kompilat;
        private final SimpleStringProperty satelita;
        private final SimpleStringProperty main;
        private final SimpleStringProperty prep;
        private final SimpleStringProperty rel;
        private final SimpleStringProperty faza3;
        private final SimpleStringProperty prepg;
        private final SimpleStringProperty prod;
        
        Kompilat(Integer id, String kompilat, String satelita, String main, String prep, String rel, String faza3, String prepg, String prod){
            this.id = new SimpleIntegerProperty(id);
            this.kompilat = new SimpleStringProperty(kompilat); 
            this.satelita = new SimpleStringProperty(satelita);
            this.main = new SimpleStringProperty(main);
            this.prep = new SimpleStringProperty(prep);
            this.rel = new SimpleStringProperty(rel);
            this.faza3 = new SimpleStringProperty(faza3);
            this.prepg = new SimpleStringProperty(prepg);
            this.prod = new SimpleStringProperty(prod);
        } 
        public Integer getId() {
            return id.get();
        }
        
        public String getKompilat() {
            return kompilat.get();
        }
        
        public String getSatelita() {
            return satelita.get();
        }
        
        public String getMain() {
            return main.get();
        }
        public String getPrep() {
            return prep.get();
        }
        public String getRel() {
            return rel.get();
        }
        public String getFaza3() {
            return faza3.get();
        }
        public String getPrepg() {
            return prepg.get();
        }
        public String getProd() {
            return prod.get();
        }
    }
    

