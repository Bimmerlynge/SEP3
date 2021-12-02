DROP SCHEMA IF EXISTS musicstreaming CASCADE;
CREATE SCHEMA musicstreaming;
SET SCHEMA 'musicstreaming';

CREATE DOMAIN _date AS DATE
    CHECK ( VALUE > TO_DATE('01-Jan-1920', 'dd-Mon-yyyy'));

CREATE TABLE IF NOT EXISTS Song
(
    songId          SERIAL PRIMARY KEY,
    songTitle       VARCHAR  NOT NULL UNIQUE,
    songDuration    SMALLINT NOT NULL,
    songReleaseYear SMALLINT,
    mp3             bytea
);


CREATE TABLE IF NOT EXISTS Album
(
    albumId       SERIAL PRIMARY KEY,
    albumTitle    VARCHAR(100) NOT NULL,
    albumDuration SMALLINT
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

CREATE TABLE IF NOT EXISTS AlbumSongRelation
(
    albumId SMALLINT,
    songId  SMALLINT,
    PRIMARY KEY (albumId, songId),
    FOREIGN KEY (albumId) REFERENCES Album (albumId) ON DELETE CASCADE,
    FOREIGN KEY (songId) REFERENCES Song (songId) ON DELETE CASCADE
);
CREATE TABLE IF NOT EXISTS AlbumArtistRelation
(
    albumId  SMALLINT,
    artistId SMALLINT,
    PRIMARY KEY (albumId, artistId),
    FOREIGN KEY (albumId) REFERENCES Album (albumId) ON DELETE CASCADE,
    FOREIGN KEY (artistId) REFERENCES Artist (artistId) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS AlbumRelease
(
    artistId SMALLINT,
    albumId  SMALLINT,
    PRIMARY KEY (albumId, artistId),
    FOREIGN KEY (albumId) REFERENCES Album (albumId),
    FOREIGN KEY (artistId) REFERENCES Artist (artistId)
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
    FOREIGN KEY (playlistId) REFERENCES Playlist (playlistId),
    FOREIGN KEY (songId) REFERENCES Song (songId)
);

CREATE VIEW SongWithArtist AS
SELECT S.*, A.*
FROM Song AS S
         JOIN ArtistSongRelation ASR ON S.songId = ASR.songId
         JOIN Artist A ON A.artistId = ASR.artistId
ORDER BY songId ASC;


CREATE VIEW AlbumsWithArtist AS
SELECT Al.*, Ar.*
FROM Artist Ar
         JOIN AlbumArtistRelation AAR ON Ar.artistId = AAR.artistId
         JOIN Album Al ON Al.albumId = AAR.albumId
ORDER BY albumId ASC;

CREATE VIEW AllSongs AS
SELECT A2.*, Ar.*, S.*
FROM Song AS S
         JOIN ArtistSongRelation ASR ON S.songId = ASR.songId
         JOIN Artist Ar ON ASR.artistId = Ar.artistId
         JOIN AlbumSongRelation A ON S.songId = A.songId
         JOIN Album A2 ON A.albumId = A2.albumId
ORDER BY songId ASC
;

CREATE VIEW PlaylistWithSongs AS
SELECT PSR.*
FROM Song AS S
         JOIN PlaylistSongRelation PSR ON S.songId = PSR.songId
         JOIN Playlist P ON P.playlistId = PSR.playlistId
ORDER BY PSR.playlistId ASC;

CREATE OR REPLACE FUNCTION songDurationTotal()
    RETURNS TRIGGER
    LANGUAGE plpgsql
AS
$$
BEGIN
    WITH durationTotal AS (
        SELECT SUM(S.songDuration) AS durTot
        FROM AlbumSongRelation
                 JOIN Song S ON S.songId = AlbumSongRelation.songId
        WHERE AlbumSongRelation.albumId = NEW.albumId)
    UPDATE Album
    SET albumDuration = durationTotal.durTot
    FROM durationTotal
    WHERE NEW.albumId = albumId;
    RETURN new;
END;
$$;

CREATE TRIGGER updateSongDuration
    AFTER INSERT OR UPDATE OR DELETE
    ON AlbumSongRelation
    FOR EACH ROW
EXECUTE PROCEDURE songDurationTotal();

INSERT INTO _User(username, password, role)
VALUES ('Admin', 'Admin', 'Admin');

INSERT INTO Playlist(playlistTitle, username) VALUES ('Fuck dig', 'Admin');
INSERT INTO PlaylistSongRelation(playlistId, songId) VALUES (1,1);

SELECT *
FROM AllSongs;

SELECT *
FROM Playlist WHERE playlistId = 1;

SELECT * FROM PlaylistSongRelation;