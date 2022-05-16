package com.example.proiectextins.service;

import com.example.proiectextins.domain.Message;
import com.example.proiectextins.repository.Repository;

import java.util.*;

public class MessageService {
    private Repository<Long, Message> repo;

    public MessageService(Repository<Long, Message> repo){
        this.repo = repo;
    }

    public MessageService(){}

    public void addMessage(String from, String to, String messageText){
        java.sql.Date date = new java.sql.Date(System.currentTimeMillis());
        Message message = new Message(from, to, messageText, date);
        repo.save(message);
    }

    public void deleteConversation(String from, String to){
        for(Message m : repo.findAll()){
            if(
                    (Objects.equals(m.getFrom(), from) && Objects.equals(m.getTo(), to)) ||
                    (Objects.equals(m.getFrom(), to) && Objects.equals(m.getTo(), from))){
                repo.delete(m.getID());
            }
        }
    }

    public Iterable<Message> getAll() { return repo.findAll(); }

    public Message findOne(Long id) { return repo.findOne(id); }

    public Iterable<Message> findMessagesInConversation(String from, String to){
        Set<Message> messages = new HashSet<>();
        Iterable<Message> all = repo.findAll();
        for(Message m : all){
            if((
                Objects.equals(m.getFrom(), from) && Objects.equals(m.getTo(), to)) ||
                Objects.equals(m.getFrom(), to) && Objects.equals(m.getTo(), from)){
                messages.add(m);
            }
        }
        return messages;
    }

    public Iterable<Message> findOnesMessages(String username){
        Set<Message> messages = new HashSet<>();
        Iterable<Message> all = repo.findAll();
        for(Message m : all){
            if(Objects.equals(m.getFrom(), username) || Objects.equals(m.getTo(), username)){
                messages.add(m);
            }
        }
        return messages;
    }

    public List<Message> findConversation(String from, String to){
        Iterable<Message> all = repo.findAll();

        List<Message> messagesList = new ArrayList<Message>();

        for(Message m : all){
            if((Objects.equals(m.getFrom(), from) && Objects.equals(m.getTo(), to)) ||
                (Objects.equals(m.getFrom(), to) && Objects.equals(m.getTo(), from))){
                messagesList.add(m);
            }
        }
        return messagesList;
    }

    public int sizeOfIterable(Iterable<Message> messages){
        int count = 0;
        for(Message m : messages){
            count ++;
        }
        return count;
    }
}
