package com.likorn_devaki.wordbook.repos;

import com.likorn_devaki.wordbook.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagsRepository extends JpaRepository<Tag, Integer> {
}
