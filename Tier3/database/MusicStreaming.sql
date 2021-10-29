DROP SCHEMA IF EXISTS musicstreaming CASCADE;
CREATE SCHEMA musicstreaming;
SET SCHEMA 'musicstreaming';

CREATE DOMAIN _date AS DATE
    CHECK ( VALUE > TO_DATE('01-Jan-1920', 'dd-Mon-yyyy'));

CREATE TABLE IF NOT EXISTS Song
(
    songId      SERIAL PRIMARY KEY,
    url         VARCHAR  NOT NULL,
    title       VARCHAR  NOT NULL,
    duration    SMALLINT NOT NULL,
    releaseDate _date
);

CREATE TABLE IF NOT EXISTS Album
(
    albumId     SERIAL PRIMARY KEY,
    title       VARCHAR(100) NOT NULL,
    duration    SMALLINT, -- Lav trigger for at finde summen af alle sange på albummet
    releaseDate _date
);

CREATE TABLE IF NOT EXISTS Artist
(
    artistId SERIAL PRIMARY KEY,
    name     VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS ArtistSongRelation
(
    artistId SMALLINT,
    songId   SMALLINT,
    PRIMARY KEY (artistId, songId),
    FOREIGN KEY (artistId) REFERENCES Artist (artistId),
    FOREIGN KEY (songId) REFERENCES Song (songId)
);

CREATE TABLE IF NOT EXISTS AlbumSpec
(
    albumId SMALLINT,
    songId  SMALLINT,
    PRIMARY KEY (albumId, songId),
    FOREIGN KEY (albumId) REFERENCES Album (albumId),
    FOREIGN KEY (songId) REFERENCES Song (songId)
);

CREATE TABLE IF NOT EXISTS AlbumRelease
(
    artistId SMALLINT,
    albumId  SMALLINT,
    PRIMARY KEY (albumId, artistId),
    FOREIGN KEY (albumId) REFERENCES Album (albumId),
    FOREIGN KEY (artistId) REFERENCES Artist (artistId)
);

CREATE OR REPLACE FUNCTION songDurationTotal()
    RETURNS TRIGGER
    LANGUAGE plpgsql
AS
$$
BEGIN
    WITH durationTotal AS (
        SELECT SUM(S.duration) AS durTot
        FROM AlbumSpec
                 JOIN Song S ON S.songId = AlbumSpec.songId
        WHERE AlbumSpec.albumId = NEW.albumId)
    UPDATE Album
    SET duration = durationTotal.durTot
    FROM durationTotal
    WHERE NEW.albumId = albumId;
    RETURN new;
END;
$$;

CREATE TRIGGER updateSongDuration
    AFTER INSERT OR UPDATE OR DELETE
    ON AlbumSpec
    FOR EACH ROW
EXECUTE PROCEDURE songDurationTotal();


INSERT INTO Album (title)
VALUES ('Test');
INSERT INTO Song(url, title, duration) VALUES
    ('..\..\..\Util\Audio\06 Ring of Fire.mp3', 'Ring_Of_Fire', 24);
INSERT INTO Song(url, title, duration) VALUES
    ('..\..\..\Util\Audio\02 Champion (feat_ Jon).mp3', 'Champion', 55);
INSERT INTO Song(url, title, duration) VALUES
    ('..\..\..\Util\Audio\11 Under the Bridge.mp3', 'Under_The_Bridge', 24);

INSERT INTO AlbumSpec (albumId, songId)
VALUES (1, 1);
SELECT duration
FROM Album;
INSERT INTO AlbumSpec (albumId, songId)
VALUES (1, 2);
SELECT duration
FROM Album;
DELETE
FROM AlbumSpec
WHERE songId = 1;
DELETE
FROM AlbumSpec
WHERE songId = 2;
SELECT duration
FROM Album;
INSERT INTO AlbumSpec (albumId, songId)
VALUES (1, 3);
SELECT duration
FROM Album;

SELECT *
FROM Song;