DROP SCHEMA IF EXISTS musicstreaming CASCADE;
CREATE SCHEMA musicstreaming;
SET SCHEMA 'musicstreaming';

CREATE DOMAIN _date AS DATE
    CHECK ( VALUE > TO_DATE('01-Jan-1920', 'dd-Mon-yyyy'));

CREATE TABLE IF NOT EXISTS Song
(
    songId      SERIAL PRIMARY KEY,
    url         VARCHAR  NOT NULL,
    songTitle       VARCHAR  NOT NULL,
    songDuration    SMALLINT NOT NULL,
    songReleaseDate _date
);

CREATE TABLE IF NOT EXISTS Album
(
    albumId     SERIAL PRIMARY KEY,
    albumTitle       VARCHAR(100) NOT NULL,
    albumDuration    SMALLINT, -- Lav trigger for at finde summen af alle sange på albummet
    albumReleaseDate _date
    );

CREATE TABLE IF NOT EXISTS Artist
(
    artistId SERIAL PRIMARY KEY,
    artistName     VARCHAR(100)
    );

CREATE TABLE IF NOT EXISTS ArtistSongRelation
(
    artistId SMALLINT,
    songId   SMALLINT,
    PRIMARY KEY (artistId, songId),
    FOREIGN KEY (artistId) REFERENCES Artist (artistId),
    FOREIGN KEY (songId) REFERENCES Song (songId)
    );

CREATE TABLE IF NOT EXISTS AlbumSongRelation
(
    albumId SMALLINT,
    songId  SMALLINT,
    PRIMARY KEY (albumId, songId),
    FOREIGN KEY (albumId) REFERENCES Album (albumId),
    FOREIGN KEY (songId) REFERENCES Song (songId)
    );
CREATE TABLE IF NOT EXISTS AlbumArtistRelation
(
    albumId SMALLINT,
    artistId SMALLINT,
    PRIMARY KEY (albumId, artistId),
    FOREIGN KEY (albumId) REFERENCES Album(albumId),
    FOREIGN KEY (artistId) REFERENCES Artist(artistId)
);

CREATE TABLE IF NOT EXISTS AlbumRelease
(
    artistId SMALLINT,
    albumId  SMALLINT,
    PRIMARY KEY (albumId, artistId),
    FOREIGN KEY (albumId) REFERENCES Album (albumId),
    FOREIGN KEY (artistId) REFERENCES Artist (artistId)
    );

CREATE VIEW SongWithArtist AS
    SELECT S.*, A.* FROM Song AS S JOIN ArtistSongRelation ASR ON S.songId = ASR.songId
JOIN Artist A ON A.artistId = ASR.artistId
ORDER BY songId ASC;


CREATE VIEW AlbumsWithArtist AS
    SELECT Al.*, Ar.* FROM Artist Ar Join AlbumArtistRelation AAR ON Ar.artistId = AAR.artistId
JOIN Album Al ON Al.albumId = AAR.albumId
ORDER BY albumId ASC;

CREATE VIEW AllSongs AS
    SELECT A2.*, Ar.*, S.* From Song AS S JOIN ArtistSongRelation ASR ON S.songId = ASR.songId JOIN Artist Ar ON ASR.artistId = Ar.artistId
    JOIN AlbumSongRelation A ON S.songId = A.songId JOIN Album A2 ON A.albumId = A2.albumId
ORDER BY songId ASC
;



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

INSERT INTO Song(url, songTitle, songDuration) VALUES
    ('..\..\..\Util\Audio\Ring_Of_Fire.mp3', 'Ring_Of_Fire', 24);
INSERT INTO Song(url, songTitle, songDuration) VALUES
    ('..\..\..\Util\Audio\Champion.mp3', 'Champion', 55);
INSERT INTO Song(url, songTitle, songDuration) VALUES
    ('..\..\..\Util\Audio\Under_The_Bridge.mp3', 'Under_The_Bridge', 24);

INSERT INTO Artist (artistName)
VALUES ('Clemens');
INSERT INTO Artist (artistName)
VALUES ('Jon');
INSERT INTO Artist (artistName)
VALUES ('Johnny Cash');
INSERT INTO Artist (artistName)
VALUES ('Red Hot Chili Peppers'), ('TestArtist4');

INSERT INTO Album (albumTitle)
VALUES ('RedHotAlbum'), ('NogetMedJohnnyAlbummet'), ('Test1'),('Test2'),('Test3');


INSERT INTO ArtistSongRelation (artistId, songId) VALUES (1,1), (2,1),(3,1),
                                                         (2,2),
                                                         (3,3)
                                                         ;


INSERT INTO AlbumSongRelation (albumId, songId)
VALUES (1,1), (2,1),
       (2,2),
       (1,3)
       ;



SELECT *
FROM AllSongs
WHERE songTitle = 'Champion';
