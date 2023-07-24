package com.mosaic.balance.service;

import com.mosaic.balance.dto.CommentDTO;
import com.mosaic.balance.dto.GameDTO;

public interface GameService {

    /**
     * Create Game
     * @param gameCreateDTO
     * @return PK of created game and created Date
     * @throws Exception when File UL fails [TBD]
     */
    public GameDTO.GameCreatedDTO createGame(GameDTO.GameCreateDTO gameCreateDTO) throws Exception;

    /**
     * Get Game details
     * @param gameId
     * @param userIdentifier might be IP address or user seq.
     * @return detail with/without result
     * @throws java.util.NoSuchElementException if element not found
     */
    public GameDTO.GameDetailResponseDTO gameDetail(long gameId, long userIdentifier, CommentDTO.RequestReadDTO commentReadDTO);

    /**
     * Modify Game information
     * @param gameId
     * @param gameCreateDTO
     * @return PK of created game, uploaded image url and Created Date
     * @throws java.util.NoSuchElementException if not found
     * @throws Exception when File UL/delete fails [TBD]
     */
    public GameDTO.GameModifiedDTO modifyGame(long gameId, GameDTO.GameCreateDTO gameCreateDTO) throws Exception;

    /**
     * Delete Game
     * @param gameId
     * @param pw
     * @return 0 for success
     * @throws java.util.NoSuchElementException if not found
     * @throws org.springframework.security.access.AccessDeniedException unauthorized user
     * @throws Exception when File UL/delete fails [TBD]
     */
    public int deleteGame(long gameId, String pw) throws Exception;
}
