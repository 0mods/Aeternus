package team.zeds.ancientmagic.client.projectile;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.Vec3;
import org.lwjgl.opengl.GL11;
import team.zeds.ancientmagic.client.render.ModShaders;

import static team.zeds.ancientmagic.api.mod.Constant.reloc;

public class ImprovedParticle extends TextureSheetParticle {
    private final boolean corrupt;
    public final boolean fake;
    public final int particle = 16;
    private final boolean slowdown = true;
    private final SpriteSet sprite;

    public ImprovedParticle(ClientLevel world, double x, double y, double z, float size,
                     float red, float green, float blue, int m,
                     boolean fake, boolean noClip, boolean corrupt, SpriteSet sprite) {
        super(world, x, y, z, 0.0D, 0.0D, 0.0D);
        rCol = red;
        gCol = green;
        bCol = blue;
        alpha = 0.75F;
        gravity = 0;
        xd = yd = zd = 0;
        quadSize = (this.random.nextFloat() * 0.5F + 0.5F) * 0.2F * size;
        lifetime = 3 * m;
        setSize(0.01F, 0.01F);
        xo = x;
        yo = y;
        zo = z;
        this.fake = fake;
        this.corrupt = corrupt;
        this.hasPhysics = !fake && !noClip;
        this.sprite = sprite;
        setSpriteFromAge(sprite);
    }

    @Override
    public float getQuadSize(float p_107681_) {
        return quadSize * (lifetime - age + 1) / lifetime;
    }

    @Override
    public void tick() {
        setSpriteFromAge(sprite);

        xo = x;
        yo = y;
        zo = z;

        if (age++ >= lifetime) {
            remove();
        }

        yd -= 0.04D * gravity;

        if (hasPhysics && !fake) {
            wiggleAround(x, (getBoundingBox().minY + getBoundingBox().maxY) / 2.0D, z);
        }

        this.move(xd, yd, zd);

        if (slowdown) {
            xd *= 0.908000001907348633D;
            yd *= 0.908000001907348633D;
            zd *= 0.908000001907348633D;

            if (onGround) {
                xd *= 0.69999998807907104D;
                zd *= 0.69999998807907104D;
            }
        }

        if (fake && age > 1) {
            remove();
        }
    }

    private void wiggleAround(double x, double y, double z) {
        BlockPos blockpos = BlockPos.containing(x, y, z);
        Vec3 Vector3d = new Vec3(x - (double) blockpos.getX(), y - (double) blockpos.getY(), z - (double) blockpos.getZ());
        BlockPos.MutableBlockPos blockpos$mutable = new BlockPos.MutableBlockPos();
        Direction direction = Direction.UP;
        double d0 = Double.MAX_VALUE;

        for (Direction direction1 : new Direction[] { Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST, Direction.UP }) {
            blockpos$mutable.set(blockpos).move(direction1);
            if (!this.level.getBlockState(blockpos$mutable).isCollisionShapeFullBlock(this.level, blockpos$mutable)) {
                double d1 = Vector3d.get(direction1.getAxis());
                double d2 = direction1.getAxisDirection() == Direction.AxisDirection.POSITIVE ? 1.0D - d1 : d1;
                if (d2 < d0) {
                    d0 = d2;
                    direction = direction1;
                }
            }
        }

        // Botania - made multiplier and add both smaller
        float f = this.random.nextFloat() * 0.05F + 0.025F;
        float f1 = (float) direction.getAxisDirection().getStep();
        // Botania - Randomness in other axes as well
        float secondary = (random.nextFloat() - random.nextFloat()) * 0.1F;
        float secondary2 = (random.nextFloat() - random.nextFloat()) * 0.1F;
        if (direction.getAxis() == Direction.Axis.X) {
            xd = (double) (f1 * f);
            yd = secondary;
            zd = secondary2;
        } else if (direction.getAxis() == Direction.Axis.Y) {
            xd = secondary;
            yd = (double) (f1 * f);
            zd = secondary2;
        } else if (direction.getAxis() == Direction.Axis.Z) {
            xd = secondary;
            yd = secondary2;
            zd = (double) (f1 * f);
        }
    }

    private static void beginRenderCommon(BufferBuilder buffer, TextureManager textureManager) {
        Minecraft.getInstance().gameRenderer.lightTexture().turnOnLightLayer();
        RenderSystem.enableDepthTest();
        RenderSystem.depthMask(false);
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
        RenderSystem.setShaderTexture(0, TextureAtlas.LOCATION_PARTICLES);
        buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.PARTICLE);
    }

    private static void endRenderCommon() {
        RenderSystem.disableBlend();
        RenderSystem.depthMask(true);
    }

    @Override
    public ParticleRenderType getRenderType() {
        return SHADERED;
    }

    public static final ParticleRenderType SHADERED = new ParticleRenderType() {
        @Override
        public void begin(BufferBuilder p_107436_, TextureManager p_107437_) {
            beginRenderCommon(p_107436_, p_107437_);
            RenderSystem.setShader(ModShaders.getInstance()::getImprovedParticle);
        }

        @Override
        public void end(Tesselator p_107438_) {
            p_107438_.end();
            endRenderCommon();
        }

        @Override
        public String toString() {
            return reloc("shadered").toString();
        }
    };
}
