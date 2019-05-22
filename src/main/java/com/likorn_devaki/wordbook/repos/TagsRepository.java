package com.likorn_devaki.wordbook.repos;

import com.likorn_devaki.wordbook.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.constraints.Max;
import java.util.List;

public interface TagsRepository extends JpaRepository<Tag, Integer> {
    List<Tag> findAllByUserId(Integer userId);
}
