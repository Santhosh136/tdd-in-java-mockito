package dev.sans.wand;

import dev.sans.inventory.InventoryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WandServiceTest {

    @Mock
    WandRepository wandRepository;
    @Mock
    InventoryService inventoryService;

    @InjectMocks
    private WandService wandService;

    @Test
    void shouldNotBeNull() {
        assertNotNull(wandService);
    }

    @Test
    void shouldCreateWand() {
        String wood = "Elder";
        String core = "Dragon";
        int lengthInCm = 18;

        when(wandRepository.save(any(Wand.class))).thenReturn(true);
        doNothing().when(inventoryService).addEntry(any(Wand.class));

        Wand created = wandService.create(wood, core, lengthInCm);

        assertNotNull(created);
        assertNotNull(created.id());
        assertEquals(wood, created.wood());
        assertEquals(core, created.core());

        verify(wandRepository, times(1)).save(any(Wand.class));

    }

    @Test
    void shouldThrowExceptionWhenWandCreationFailedFromRepo() {
        String wood = "Elder";
        String core = "Dragon";
        int lengthInCm = 18;
        String errorMessage = "Wand not created";

        when(wandRepository.save(any(Wand.class))).thenReturn(false);

        WandOperationException exception = assertThrows(WandOperationException.class, () -> wandService.create(wood, core, lengthInCm));
        assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionIfWoodIsMissing() {
        String wood = "";
        String core = "Dragon";
        int lengthInCm = 18;
        String errorMessage = "Wood is required";

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> wandService.create(wood, core, lengthInCm));
        assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenInventoryServiceThrowsException() {
        String wood = "Elder";
        String core = "Dragon";
        int lengthInCm = 18;

        when(wandRepository.save(any(Wand.class))).thenReturn(true);
        doThrow(RuntimeException.class).when(inventoryService).addEntry(any(Wand.class));

        assertThrows(RuntimeException.class, () ->
                wandService.create(wood, core, lengthInCm)
        );
    }

    @Test
    void shouldCallRealInventoryServiceMethod() {
        String wood = "Elder";
        String core = "Dragon";
        int lengthInCm = 18;

        when(wandRepository.save(any(Wand.class))).thenReturn(true);
        doCallRealMethod().when(inventoryService).addEntry(any(Wand.class));

        Wand wand = wandService.create(wood, core, lengthInCm);

        assertNotNull(wand);
        assertEquals(wood, wand.wood());
        assertEquals(core, wand.core());
    }

}