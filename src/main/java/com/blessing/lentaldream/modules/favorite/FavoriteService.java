package com.blessing.lentaldream.modules.favorite;

import com.blessing.lentaldream.modules.account.domain.Account;
import com.blessing.lentaldream.modules.account.repository.AccountRepository;
import com.blessing.lentaldream.modules.post.PostService;
import com.blessing.lentaldream.modules.post.domain.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class FavoriteService {
    private final AccountRepository accountRepository;
    private final PostService postService;

    public void addFavoriteToAccount(Account account, Long postId){
        Account foundAccount = accountRepository.findByNickname(account.getNickname());
        Post post = postService.loadPostInformation(postId);
        Favorite favorite = Favorite.createNewFavorite(account, post);
        if(!foundAccount.checkFavorite(post)) {
            post.increaseFavoriteCount();
            foundAccount.addFavorite(favorite);
        }
    }

    public void deleteFavoriteFromAccount(Account account, Long postId) {
        Account foundAccount = accountRepository.findByNickname(account.getNickname());
        Post post = postService.loadPostInformation(postId);
        Favorite target = Favorite.createNewFavorite(account,post);
        if(foundAccount.checkFavorite(post)) {
            foundAccount.deleteFavorite(target);
            post.decreaseFavoriteCount();
        }
    }
}
