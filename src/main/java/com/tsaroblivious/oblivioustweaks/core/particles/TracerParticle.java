package com.tsaroblivious.oblivioustweaks.core.particles;

import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.SpriteTexturedParticle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TracerParticle extends SpriteTexturedParticle {

	protected TracerParticle(ClientWorld world, double x, double y, double z, double xVel, double yVel, double zVel) {
		super(world, x, y, z, xVel, yVel, zVel);

		float f = this.random.nextFloat() * 1.0f;
		this.rCol = f;
		this.gCol = f;
		this.bCol = f;
		this.alpha = 0.8f;
		this.xd = 0;
		this.yd = 0;
		this.zd = 0;

		this.setSize(0.02f, 0.02f);

		this.scale(this.random.nextFloat() * 1.1f);
		this.age = 1;

	}

	@Override
	public IParticleRenderType getRenderType() {
		return IParticleRenderType.PARTICLE_SHEET_OPAQUE;
	}

	@Override
	public void tick() {
		this.xo = this.x;
		this.yo = this.y;
		this.zo = this.z;
		if (this.age-- <= 0) {
			this.remove();
		} else {
			this.move(this.xd, this.yd, this.zd);
			this.xd *= 1.0d;
			this.yd *= 1.0d;
			this.zd *= 1.0d;
		}

	}

	@OnlyIn(Dist.CLIENT)
	public static class Factory implements IParticleFactory<BasicParticleType> {

		private final IAnimatedSprite spriteSet;

		public Factory(IAnimatedSprite sprite) {
			this.spriteSet = sprite;
		}

		@Override
		public Particle createParticle(BasicParticleType p_199234_1_, ClientWorld p_199234_2_, double p_199234_3_,
				double p_199234_5_, double p_199234_7_, double p_199234_9_, double p_199234_11_, double p_199234_13_) {
			TracerParticle tracer = new TracerParticle(p_199234_2_, p_199234_3_, p_199234_5_, p_199234_7_, p_199234_9_,
					p_199234_11_, p_199234_13_);
			tracer.setColor(1, 1, 1);
			tracer.pickSprite(this.spriteSet);
			return tracer;
		}

	}

}
