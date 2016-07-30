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

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent.KeyInputEvent;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.StatCollector;

/**
 * @author andylizi
 */
@Mod(modid = ExternalInputUI.MODID, name = ExternalInputUI.NAME, version = ExternalInputUI.VERSION, acceptedMinecraftVersions = "1.7.10")
public class ExternalInputUI {
    public static final String MODID = "externalinputui";
    public static final String NAME = "ExternalInputUI";
    public static final String VERSION = "1.0.0";
    @Mod.Instance(ExternalInputUI.MODID)
    public static ExternalInputUI instance;
    
    KeyBinding keyBinding;
    ExecutorService executor = Executors.newCachedThreadPool(new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(null, r, MODID);
            thread.setDaemon(true);
            return thread;
        }
    });
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event){
        keyBinding = new KeyBinding("key.open_external_input", 23, "key.categories.misc"); // 'I'
        ClientRegistry.registerKeyBinding(keyBinding);
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event){
        FMLCommonHandler.instance().bus().register(this);
        InputGUI.initLAF();
    }
    
    public void openInputUI(){
        String str = InputGUI.showInputDialog(StatCollector.translateToLocal("gui.externalinput.title"), 
                StatCollector.translateToLocal("gui.done"), 
                StatCollector.translateToLocal("gui.cancel"));
        if(str == null)
            return;
        if(str.isEmpty())
            return;
        Minecraft mc = Minecraft.getMinecraft();
        mc.thePlayer.sendChatMessage(str);
        try {
            Thread.sleep(100);
        } catch(InterruptedException ex) {}
        mc.displayGuiScreen(null);
    }
    
    @SubscribeEvent
    public void onKey(KeyInputEvent.KeyInputEvent event){
        if(keyBinding.isPressed()){
            executor.submit(new Runnable() {
                @Override
                public void run() {
                    openInputUI();
                }
            });
        }
    }
}
