#!//usr//bin//ruby
world.setAmbientLight(Color.new(127,127,127))
base = world.createMovableDoodad(GraphicMesh.new("turret_base.mesh", Material.get("planet")))
turretE = world.createMovableDoodad(GraphicMesh.new("turret_canon.mesh", Material.get("planet")))
target = world.createMovableDoodad(Box.new(2), Material.get("planet"))
target.setPosition(5, 200, 5)
camera.setPosition(500, 500, 500)
camera.lookAt(0,0,0)
laser = world.createLaser()
turret = Turret.new(base, turretE, 30, laser)
engine.addFrameListener(turret)
turret.setTarget(target)
