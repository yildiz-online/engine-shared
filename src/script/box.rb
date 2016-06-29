#!//usr//bin//ruby
world.setAmbientLight(Color.new(127,127,127))
box = world.createMovableDoodad(GraphicMesh.new("turret_canon.mesh", Material.get("planet")))
camera.setPosition(500, 0, 500)
camera.lookAt(0,0,0)
