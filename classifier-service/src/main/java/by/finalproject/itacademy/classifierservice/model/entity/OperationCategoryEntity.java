package by.finalproject.itacademy.classifierservice.model.entity;

import by.finalproject.itacademy.common.model.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "operation_categories")
public class OperationCategoryEntity {

    @EmbeddedId
    private BaseEntity baseEntity;

    @Column(nullable = false, unique = true)
    private String title;

}
