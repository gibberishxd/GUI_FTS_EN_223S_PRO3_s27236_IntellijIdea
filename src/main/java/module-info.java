module com.example.gui_fts_en_223s_pro3_s27236_intellijidea {
    requires javafx.controls;
    requires javafx.fxml;
            
        requires org.controlsfx.controls;
            requires com.dlsc.formsfx;
                        
    opens com.example.gui_fts_en_223s_pro3_s27236_intellijidea to javafx.fxml;
    exports com.example.gui_fts_en_223s_pro3_s27236_intellijidea;
}