package com.challenge.ml.bsn;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.challenge.ml.beans.BookVO;
import com.challenge.ml.beans.WishListVO;
import com.challenge.ml.dao.BooksRepository;
import com.challenge.ml.dao.UsersRepository;
import com.challenge.ml.dao.WishListRepository;
import com.challenge.ml.entity.Book;
import com.challenge.ml.entity.Users;
import com.challenge.ml.entity.Wishlist;

@Service
public class WishListBsnImpl implements WishLisBsn{
	@Autowired
	BooksRepository booksRepository;
	@Autowired
	WishListRepository wishListRepository;
	@Autowired
	UsersRepository usersRepository;
	@Autowired
	private ModelMapper mapper;

	@Override
	public void saveNewWishList(@RequestBody List<BookVO> bookVO,HttpSession session) {
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		WishListVO wishListVO=new WishListVO();
		Users users=usersRepository.getById((int)session.getAttribute("id"));
		wishListVO.setIdUser(users.getIdUsers());
		wishListVO.setBook(bookVO);
		Wishlist newWishlist=mapper.map(wishListVO, Wishlist.class);
		System.out.println(newWishlist.toString());
		wishListRepository.save(newWishlist);
	}

}
