#!//usr//bin//ruby
box = Box.new 5
world = GameEngine.instance.activeWorld
world.createMovableDoodad(box, Material.get("console_input"))
camera = world.defaultCamera
camera.setPosition 50,50,50
camera.lookAt(0,0,0)
