package worldcontrolteam.worldcontrol.api.card;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public interface ICardHandler {
    String getCardName();

    boolean isValidBlock(IBlockAccess world, BlockPos pos);

    Card createCard(IBlockAccess world, BlockPos pos);
}