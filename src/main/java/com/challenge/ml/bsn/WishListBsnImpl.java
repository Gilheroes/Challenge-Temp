package com.challenge.ml.bsn;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.servlet.http.HttpSession;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.EntityManagerFactoryUtils;
import org.springframework.stereotype.Service;

import com.challenge.ml.beans.BookVO;
import com.challenge.ml.beans.UsersVO;
import com.challenge.ml.beans.WishListVO;
import com.challenge.ml.dao.BooksRepository;
import com.challenge.ml.dao.UsersRepository;
import com.challenge.ml.dao.WishListRepository;
import com.challenge.ml.entity.Book;
import com.challenge.ml.entity.Users;
import com.challenge.ml.entity.Wishlist;

@Service
public class WishListBsnImpl implements WishLisBsn {

    @Autowired
    private BooksRepository booksRepository;
    @Autowired
    private WishListRepository wishListRepository;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private ModelMapper mapper;

    @Override
    public void saveNewWishList(BookVO bookVO, String nameOfWishList, HttpSession session) {
        System.out.println("Nombre: " + nameOfWishList);
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        WishListVO wishListVO = new WishListVO();
        wishListVO.setNameOfList(nameOfWishList);
        Users users = usersRepository.getById((int) session.getAttribute("id"));
        wishListVO.setIdUser(users.getIdUsers());
        wishListVO.getBook().add(bookVO);
        wishListVO.setNameOfList(nameOfWishList);
        Wishlist newWishlist = mapper.map(wishListVO, Wishlist.class);
        wishListRepository.save(newWishlist);
        System.out.println(wishListRepository.findAll());
    }

    @Override
    public WishListVO updateWishList(BookVO bookVO, HttpSession session) {
        UsersVO usersVO = mapper.map(usersRepository.getById((int) session.getAttribute("id")), UsersVO.class);
        Wishlist wishlist = wishListRepository.findWishByIdUser(usersVO.getId_users());
        WishListVO wishListVO = mapper.map(wishlist, WishListVO.class);
        for (BookVO bookVOl : wishListVO.getBook()) {
            Book book = mapper.map(bookVOl, Book.class);
            if (bookVOl.getTitle().equals(bookVO.getTitle()) && bookVOl.getIdGoogle().equals(bookVO.getIdGoogle())) {
                booksRepository.delete(book);
            } else {
                bookVOl.setWishListVO(wishListVO);
                book = mapper.map(bookVOl, Book.class);
                booksRepository.save(book);
            }

        }
        wishlist = wishListRepository.findWishByIdUser(usersVO.getId_users());
        wishListVO = mapper.map(wishlist, WishListVO.class);
        return wishListVO;
    }

    @Override
    public WishListVO findWishlistByIdUser(HttpSession session) {
        System.out.println((int) session.getAttribute("id"));
        System.out.println(wishListRepository.findAll());
        WishListVO wishListVO = mapper.map(wishListRepository.findWishByIdUser((int) session.getAttribute("id")), WishListVO.class);
        return wishListVO;
    }

    @Override
    public boolean deleteWishList(Integer id, HttpSession session) {
        //Implementar metodo de borrado, considerar borrar todos los libros
        return false;
    }


}
