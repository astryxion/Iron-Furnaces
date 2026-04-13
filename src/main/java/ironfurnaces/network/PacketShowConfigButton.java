/*    */ package ironfurnaces.network;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import ironfurnaces.capability.CapabilityPlayerShowConfig;
/*    */ import ironfurnaces.capability.IPlayerShowConfig;
/*    */ import java.util.function.Supplier;
/*    */ import net.minecraft.server.level.ServerPlayer;
/*    */ import net.minecraftforge.common.capabilities.CapabilityHooks;
/*    */ import net.minecraftforge.network.NetworkEvent;
/*    */ 
/*    */ public class PacketShowConfigButton
/*    */ {
/*    */   private int set;
/*    */   
/*    */   public PacketShowConfigButton(ByteBuf buf) {
/* 15 */     this.set = buf.readInt();
/*    */   }
/*    */   
/*    */   public void toBytes(ByteBuf buf) {
/* 19 */     buf.writeInt(this.set);
/*    */   }
/*    */   
/*    */   public PacketShowConfigButton(int set) {
/* 23 */     this.set = set;
/*    */   }
/*    */   
/*    */   public void handle(Supplier<NetworkEvent.Context> supplier) {
/* 27 */     NetworkEvent.Context ctx = supplier.get();
/* 28 */     ctx.enqueueWork(() -> {
/*    */           ServerPlayer player = ctx.getSender();
/*    */           
/*    */           CapabilityHooks.get(player, CapabilityPlayerShowConfig.CONFIG).ifPresent(cap -> cap.set(PacketShowConfigButton.this.set));
/*    */         });
/* 33 */     ctx.setPacketHandled(true);
/*    */   }
/*    */ }


/* Location:              C:\Users\tinop\curseforge\minecraft\Instances\wdeqw\mods\ironfurnaces-1.20.1-4.1.8.jar!\ironfurnaces\network\PacketShowConfigButton.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */