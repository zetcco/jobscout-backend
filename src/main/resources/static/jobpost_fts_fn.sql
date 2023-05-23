DROP FUNCTION fts;
CREATE FUNCTION fts(description varchar, searchQuery varchar) RETURNS boolean AS $$
    select case when to_tsvector(description) @@ to_tsquery(searchQuery) then TRUE else FALSE end;
$$ LANGUAGE SQL;