package io.greengame.greengameio.friendmodule.managers;

import com.mongodb.MongoTimeoutException;
import io.greengame.greengameio.friendmodule.exceptions.ErrorMessages;
import io.greengame.greengameio.friendmodule.exceptions.IllegalOperationException;
import io.greengame.greengameio.friendmodule.exceptions.NotFoundException;
import io.greengame.greengameio.friendmodule.model.Chat;
import io.greengame.greengameio.friendmodule.model.Friend;
import io.greengame.greengameio.friendmodule.repositories.AbstractChatHolderRepository;
import io.greengame.greengameio.friendmodule.repositories.ChatRepository;
import io.greengame.greengameio.friendmodule.repositories.UserFMRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional(isolation = Isolation.REPEATABLE_READ)
public class FriendManager {
    private final AbstractChatHolderRepository abstractChatHolderRepository;
    private final ChatRepository chatRepository;
    private final UserFMRepository userFMRepository;

    public void sendFriendRequest(Long senderId, Long receiverId)  {
        var sender = userFMRepository.findById(senderId)
                .orElseThrow(() -> new NotFoundException(ErrorMessages.NotFoundErrorMessages.USER_NOT_FOUND));
        var receiver = userFMRepository.findById(receiverId)
                .orElseThrow(() -> new NotFoundException(ErrorMessages.NotFoundErrorMessages.USER_NOT_FOUND));
        if(sender.getFriends().contains(receiver)
                || receiver.getFriends().contains(sender)
                || sender.getFriendRequests().contains(receiver)
                || receiver.getFriendRequests().contains(sender)) {
            throw new IllegalOperationException(ErrorMessages.BadRequestErrorMessages.ILLEGAL_OPERATION);
        }
        receiver.getFriendRequests().add(senderId);
        userFMRepository.save(receiver);
    }

    public void acceptFriendRequest(Long senderId, Long receiverId) {
        var sender = userFMRepository.findById(senderId)
                .orElseThrow(() -> new NotFoundException(ErrorMessages.NotFoundErrorMessages.USER_NOT_FOUND));
        var receiver = userFMRepository.findById(receiverId)
                .orElseThrow(() -> new NotFoundException(ErrorMessages.NotFoundErrorMessages.USER_NOT_FOUND));
        if(!receiver.getFriendRequests().contains(senderId)) {
            throw new IllegalOperationException(ErrorMessages.BadRequestErrorMessages.ILLEGAL_OPERATION);
        }
        //dozategowania
        Chat chat = new Chat();
        chatRepository.save(chat);
        Friend friend = new Friend();
        abstractChatHolderRepository.save(friend);
        receiver.getFriendRequests().remove(senderId);
        receiver.getFriends().add(friend.getId());
        sender.getFriends().add(friend.getId());
        userFMRepository.save(receiver);
        userFMRepository.save(sender);
    }
    public void removeFriend(Long senderId, Long receiverId) {
        var sender = userFMRepository.findById(senderId)
                .orElseThrow(() -> new NotFoundException(ErrorMessages.NotFoundErrorMessages.USER_NOT_FOUND));
        var receiver = userFMRepository.findById(receiverId)
                .orElseThrow(() -> new NotFoundException(ErrorMessages.NotFoundErrorMessages.USER_NOT_FOUND));
        if(!receiver.getFriends().contains(senderId) || !sender.getFriends().contains(receiverId)) {
            throw new IllegalOperationException(ErrorMessages.BadRequestErrorMessages.ILLEGAL_OPERATION);
        }
        var friend = abstractChatHolderRepository.findById(receiver
                        .getFriends()
                        .stream()
                        .filter(id -> id.equals(senderId))
                        .findFirst()
                        .get())
                .orElseThrow(() -> new NotFoundException(ErrorMessages.BadRequestErrorMessages.ILLEGAL_OPERATION));
        receiver.getFriends().remove(friend.getId());
        sender.getFriends().remove(friend.getId());
        abstractChatHolderRepository.delete(friend);
        userFMRepository.save(receiver);
        userFMRepository.save(sender);
    }
}
