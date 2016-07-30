/*
 * Copyright 2016 andylizi.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.andylizi.externalinputui;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;

/**
 * @author andylizi
 */
@Mod(modid = ExternalInputUI.MODID, name = ExternalInputUI.NAME, version = ExternalInputUI.VERSION, acceptedMinecraftVersions = "1.9")
public class ExternalInputUI {
    public static final String MODID = "externalinputui";
    public static final String NAME = "ExternalInputUI";
    public static final String VERSION = "1.0.0";
    
    @Mod.Instance(ExternalInputUI.MODID)
    public static ExternalInputUI instance;
    private static Minecraft minecraft = Minecraft.getMinecraft();
    
    KeyBinding keyBinding;
    ExecutorService executor = Executors.newCachedThreadPool(new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(null, r, MODID);
            thread.setDaemon(true);
            return thread;
        }
    });
    private Boolean state = false;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event){
        keyBinding = new KeyBinding("key.open_external_input", 23, "key.categories.misc"); // 'I'
        ClientRegistry.registerKeyBinding(keyBinding);
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event){
        MinecraftForge.EVENT_BUS.register(this);
        InputGUI.initLAF();
        InputGUI fake = new InputGUI("TEST", "TEST", "TEST");
    }
    
    public void openInputUI(){
        String str = InputGUI.showInputDialog(I18n.translateToLocal("gui.externalinput.title"), 
                I18n.translateToLocal("gui.done"), 
                I18n.translateToLocal("gui.cancel"));
        try{
            if(str == null)
                return;
            if(str.isEmpty())
                return;
            minecraft.thePlayer.sendChatMessage(str);
            try {
                Thread.sleep(100);
            } catch(InterruptedException ex) {}
        }finally{
            minecraft.displayGuiScreen(null);
        }
    }
    
    @SubscribeEvent
    public void onKey(KeyInputEvent.KeyInputEvent event){
        if(keyBinding.isPressed() && minecraft.thePlayer != null){
            synchronized(state){
                if(state)
                    return;
                state = true;
            }
            minecraft.displayGuiScreen(new GuiIngameMenu());
            executor.submit(new Runnable() {
                @Override
                public void run() {
                    openInputUI();
                    state = false;
                }
            });
        }
    }
}
