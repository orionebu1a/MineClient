
with open("MathUtils.java", "r") as srcFile:
    srcCode = srcFile.read()

srcCode = srcCode.replace(".rotationYaw", ".yaw")
srcCode = srcCode.replace("Wrapper.", "HackedClient.")
srcCode = srcCode.replace("EntityPlayerSP", "ClientPlayerEntity")
srcCode = srcCode.replace(".posX", ".getX()")
srcCode = srcCode.replace(".posY", ".getY()")
srcCode = srcCode.replace(".posZ", ".getZ()")
srcCode = srcCode.replace(".getEntityBoundingBox()", ".getBoundingBox()")
srcCode = srcCode.replace(".rotationPitch", ".pitch")
srcCode = srcCode.replace(".width", ".getWidth()")
srcCode = srcCode.replace(".height", ".getHeight()")
srcCode = srcCode.replace(".getDistance(", ".distanceTo(")

with open("MathUtils.java", "w") as preparedSrcFile:
    preparedSrcFile.write(srcCode)

