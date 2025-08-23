package com.example.kanbrunco.model.cartao;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("checklist")
public class CartaoComChecklist extends Cartao {

    @Transient
    private List<String> checklist = new ArrayList<>();
    
    public CartaoComChecklist() {
        super();
    }
    
    public void adicionarItemChecklist(String item) {
        this.checklist.add(item);
    }

    public void removerItemChecklist(String item) {
        this.checklist.remove(item);
    }
    
    public List<String> getChecklist() {
        return checklist;
    }

    public void setChecklist(List<String> checklist) {
        this.checklist = checklist;
    }
}

