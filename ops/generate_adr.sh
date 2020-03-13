adr generate graph > adr.tmp.dot
dot -T png adr.tmp.dot -o doc/adr_schema.png
rm adr.tmp.dot
