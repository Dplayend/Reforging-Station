Cosas que faltan en el mod:
	✓ hacer que funcione las modificaciones a las monturas de caballo
	✓ meter el mod de armaduras de lobo y darle modificaciones
	✓ lo mismo para el ice and dragons
	✓ cambiar el icono
	✓ hacer el idioma en español y sus variantes
    ✓ hacer que el tridente tenga modificaciones


/attribute @e[type=horse,sort=nearest,limit=1] minecraft:generic.armor get

/summon minecraft:zombie ~ ~ ~ {CanPickUpLoot: 1b,NoAI: 1b,ArmorItems:[{id:"minecraft:chainmail_boots",Count:1b}]}
/attribute @e[type=zombie,sort=nearest,limit=1] minecraft:generic.armor get