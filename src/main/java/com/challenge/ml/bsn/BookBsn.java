package com.challenge.ml.bsn;

import javax.servlet.http.HttpSession;

import com.challenge.ml.beans.BookVO;

import java.util.List;

public interface BookBsn {
    /**
     * Get books by wishlist name.
     *
     * @param name    Wishlist name
     * @param session User session.
     * @return A list with the books related to the wishlist name or an empty list.
     */
    List<BookVO> getBooksOfWishListByName(final String name, final HttpSession session);

    /**
     * Get books by wishlist identifier.
     *
     * @param id      Wishlist identifier.
     * @param session User session.
     * @return A list with the books related to the wishlist identifier or an empty list.
     */
    List<BookVO> getBooksOfWishListById(final Integer id, final HttpSession session);


}
