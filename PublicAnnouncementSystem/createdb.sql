create table buildings(
              BID char(5),
              BName char(50),
              BLoc MDSYS.SDO_GEOMETRY,
              primary key (BID)
);

create table students(
              personID char(10),
              SLoc MDSYS.SDO_GEOMETRY,
              primary key (personID)
);

create table aSys(
              asID char(10),
              ALoc MDSYS.SDO_GEOMETRY,
              radius float,
              primary key (asID)
);

