/*    */ package ironfurnaces.network;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import ironfurnaces.tileentity.furnaces.BlockIronFurnaceTileBase;
/*    */ import java.util.function.Supplier;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.server.level.ServerPlayer;
/*    */ import net.minecraftforge.common.world.LevelForgeHooks;
/*    */ import net.minecraftforge.network.NetworkEvent;
/*    */ 
/*    */ 
/*    */ public class PacketSettingsButton
/*    */ {
/*    */   private int x;
/*    */   private int y;
/*    */   private int z;
/*    */   private int index;
/*    */   private int set;
/*    */   
/*    */   public PacketSettingsButton(ByteBuf buf) {
/* 20 */     this.x = buf.readInt();
/* 21 */     this.y = buf.readInt();
/* 22 */     this.z = buf.readInt();
/* 23 */     this.index = buf.readInt();
/* 24 */     this.set = buf.readInt();
/*    */   }
/*    */   
/*    */   public void toBytes(ByteBuf buf) {
/* 28 */     buf.writeInt(this.x);
/* 29 */     buf.writeInt(this.y);
/* 30 */     buf.writeInt(this.z);
/* 31 */     buf.writeInt(this.index);
/* 32 */     buf.writeInt(this.set);
/*    */   }
/*    */   
/*    */   public PacketSettingsButton(BlockPos pos, int index, int set) {
/* 36 */     this.x = pos.getX();
/* 37 */     this.y = pos.getY();
/* 38 */     this.z = pos.getZ();
/* 39 */     this.index = index;
/* 40 */     this.set = set;
/*    */   }
/*    */   
/*    */   public void handle(Supplier<NetworkEvent.Context> ctx) {
/* 44 */     ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> {
/*    */           ServerPlayer player = ((NetworkEvent.Context)ctx.get()).getSender();
/*    */           BlockPos pos = new BlockPos(this.x, this.y, this.z);
/*    */           BlockIronFurnaceTileBase te = (BlockIronFurnaceTileBase)player.level().getBlockEntity(pos);
/*    */           if (player.level().isLoaded(pos)) {
/*    */             te.furnaceSettings.set(this.index, this.set);
/*    */             LevelForgeHooks.markAndNotifyBlock(te.getLevel(), pos, player.level().getChunkAt(pos), te.getLevel().getBlockState(pos).getBlock().defaultBlockState(), te.getLevel().getBlockState(pos), 2, 0);
/*    */             te.setChanged();
/*    */           } 
/*    */         });
/* 54 */     ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
/*    */   }
/*    */ }


/* Location:              C:\Users\tinop\curseforge\minecraft\Instances\wdeqw\mods\ironfurnaces-1.20.1-4.1.8.jar!\ironfurnaces\network\PacketSettingsButton.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */