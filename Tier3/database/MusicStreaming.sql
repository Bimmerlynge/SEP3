DROP SCHEMA IF EXISTS musicstreaming CASCADE;
CREATE SCHEMA musicstreaming;
SET SCHEMA 'musicstreaming';

CREATE DOMAIN _date AS DATE
    CHECK ( VALUE > TO_DATE('01-Jan-1920', 'dd-Mon-yyyy'));


CREATE TABLE IF NOT EXISTS Album
(
    albumId       SERIAL PRIMARY KEY,
    albumTitle    VARCHAR(100) NOT NULL
    );


CREATE TABLE IF NOT EXISTS Song
(
    songId          SERIAL PRIMARY KEY,
    albumId         INT,
    songTitle       VARCHAR  NOT NULL,
    songDuration    SMALLINT NOT NULL,
    songReleaseYear SMALLINT,
    songPath        VARCHAR,
    FOREIGN KEY (albumId) REFERENCES Album(albumId)
    );




CREATE TABLE IF NOT EXISTS _User
(
    username VARCHAR PRIMARY KEY,
    password VARCHAR NOT NULL,
    role     VARCHAR NOT NULL


);

CREATE TABLE IF NOT EXISTS Artist
(
    artistId   SERIAL PRIMARY KEY,
    artistName VARCHAR(100)
    );

CREATE TABLE IF NOT EXISTS ArtistSongRelation
(
    artistId SMALLINT,
    songId   SMALLINT,
    PRIMARY KEY (artistId, songId),
    FOREIGN KEY (artistId) REFERENCES Artist (artistId) ON DELETE CASCADE,
    FOREIGN KEY (songId) REFERENCES Song (songId) ON DELETE CASCADE
    );


CREATE TABLE IF NOT EXISTS Playlist
(
    playlistId    SERIAL PRIMARY KEY,
    playlistTitle VARCHAR(50) NOT NULL,
    username      VARCHAR     NOT NULL,
    FOREIGN KEY (username) REFERENCES _User (username)
    );

CREATE TABLE IF NOT EXISTS PlaylistSongRelation
(
    playlistId SMALLINT,
    songId     SMALLINT,
    PRIMARY KEY (playlistId, songId),
    FOREIGN KEY (playlistId) REFERENCES Playlist (playlistId) ON DELETE CASCADE,
    FOREIGN KEY (songId) REFERENCES Song (songId) ON DELETE CASCADE
    );


CREATE OR REPLACE FUNCTION updateSongPath()
    RETURNS TRIGGER
    LANGUAGE plpgsql
AS
$$
BEGIN
UPDATE Song
SET songPath = CONCAT(songId::VARCHAR, '_', songTitle, '.mp3')
WHERE songId = NEW.songId;
RETURN NEW;
END;
$$;

CREATE TRIGGER updateSongPath
    AFTER INSERT
    ON Song
    FOR EACH ROW
    EXECUTE PROCEDURE updateSongPath();

CREATE VIEW AllSongs AS
SELECT Ar.*, S.*, A2.albumTitle
FROM Song AS S
         JOIN ArtistSongRelation ASR ON S.songId = ASR.songId
         JOIN Artist Ar ON ASR.artistId = Ar.artistId
         JOIN Album A2 ON S.albumId = A2.albumId
ORDER BY songId ASC
;

CREATE VIEW SongWithArtist AS
SELECT S.*, A.*
FROM Song AS S
         JOIN ArtistSongRelation ASR ON S.songId = ASR.songId
         JOIN Artist A ON A.artistId = ASR.artistId
ORDER BY songId ASC;


INSERT INTO _User(username, password, role)
VALUES ('Admin', 'Admin', 'Admin');


SELECT * FROM Artist JOIN Song S ON Artist.artistId = S.albumId WHERE songId = ?;

SELECT S.songId, songTitle, songDuration, songReleaseYear, songPath, S.albumId , albumTitle
FROM PlaylistSongRelation
         JOIN Song S ON S.songId = PlaylistSongRelation.songId
         JOIN Album A ON A.albumId = S.albumId
WHERE playlistId = ?;

SELECT * From _User;