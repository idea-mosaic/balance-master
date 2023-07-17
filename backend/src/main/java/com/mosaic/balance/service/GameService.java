package com.mosaic.balance.service;

import com.mosaic.balance.dto.GameDTO;

public interface GameService {

    /**
     * Create Game
     * @param gameCreateDTO
     * @return PK of created game and created Date
     */
    public GameDTO.GameCreatedDTO createGame(GameDTO.GameCreateDTO gameCreateDTO) throws Exception;

    /**
     * Get Game details
     * @param gameId
     * @param userIdentifier might be IP address or user seq.
     * @return detail with/without result
     * @throws java.util.NoSuchElementException if element not found
     */
    public GameDTO.GameDetailDTO gameDetail(long gameId, long userIdentifier);

    /**
     * Modify Game information
     * @param gameId
     * @param gameCreateDTO
     * @return PK of created game and Created Date
     */
    public GameDTO.GameModifiedDTO modifyGame(long gameId, GameDTO.GameCreateDTO gameCreateDTO) throws Exception;

    /**
     * Delete Game
     * @param gameId
     * @param pw
     * @return 0 for success
     */
    public int deleteGame(long gameId, String pw);
}
