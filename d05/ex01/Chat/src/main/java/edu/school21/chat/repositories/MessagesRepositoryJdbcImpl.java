package edu.school21.chat.repositories;

import edu.school21.chat.models.Message;

import javax.sql.DataSource;
import java.util.Optional;

public class MessagesRepositoryJdbcImpl implements MessagesRepository{

    @Override
    public Optional<Message> findById(Long id) {


        return Optional.empty();
    }
}
