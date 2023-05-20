package team.zeromods.ancientmagic.mixin.client;

//@Mixin(ItemLayerModel.class)
public class ItemTextureModifier {
//    @Shadow
//    private ImmutableList<Material> textures;
//
//    @Shadow @Final
//    private Int2ObjectMap<ResourceLocation> renderTypeNames;
//
//    @Shadow @Final
//    private Int2ObjectMap<ForgeFaceData> layerData;
//
//    @Inject(method = "bake", at = @At("HEAD"))
//    public void bakeInject(IGeometryBakingContext context, ModelBaker baker, Function<Material, TextureAtlasSprite> spriteGetter,
//                           ModelState modelState, ItemOverrides overrides, ResourceLocation modelLocation,
//                           CallbackInfoReturnable<BakedModel> cir) {
//        if (textures == null)
//        {
//            ImmutableList.Builder<Material> builder = ImmutableList.builder();
//            for (int i = 0; context.hasMaterial("layer" + i); i++)
//            {
//                builder.add(context.getMaterial("layer" + i));
//            }
//            textures = builder.build();
//        }
//
//        TextureAtlasSprite particle = spriteGetter.apply(
//                context.hasMaterial("particle") ? context.getMaterial("particle") : textures.get(0)
//        );
//        var rootTransform = context.getRootTransform();
//        if (!rootTransform.isIdentity())
//            modelState = UnbakedGeometryHelper.composeRootTransformIntoModelState(modelState, rootTransform);
//
//        var normalRenderTypes = new RenderTypeGroup(RenderType.translucent(), ForgeRenderTypes.ITEM_UNSORTED_TRANSLUCENT.get());
//        CompositeModel.Baked.Builder builder = CompositeModel.Baked.builder(context, particle, overrides, context.getTransforms());
//        for (int i = 0; i < textures.size(); i++)
//        {
//            TextureAtlasSprite sprite = spriteGetter.apply(textures.get(i));
//            var unbaked = UnbakedGeometryHelper.createUnbakedItemElements(i, sprite.contents(), this.layerData.get(i));
//            var quads = UnbakedGeometryHelper.bakeElements(unbaked, $ -> sprite, modelState, modelLocation);
//            var renderTypeName = renderTypeNames.get(i);
//            var renderTypes = renderTypeName != null ? context.getRenderType(renderTypeName) : null;
//            builder.addQuads(renderTypes != null ? renderTypes : normalRenderTypes, quads);
//        }
//
//        cir.setReturnValue(builder.build());
//    }
}
