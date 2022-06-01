package com.alaharranhonor.swem.config;

/*
 * All Rights Reserved
 *
 * Copyright (c) 2021, AlaharranHonor, Legenden.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public final class ConfigHolder {
  public static final ForgeConfigSpec CLIENT_SPEC;
  public static final ForgeConfigSpec SERVER_SPEC;
  public static final ClientConfig CLIENT;
  public static final ServerConfig SERVER;

  static {
    final Pair<ServerConfig, ForgeConfigSpec> specPair =
        new ForgeConfigSpec.Builder().configure(ServerConfig::new);
    SERVER = specPair.getLeft();
    SERVER_SPEC = specPair.getRight();

    final Pair<ClientConfig, ForgeConfigSpec> clientSpecPair =
        new ForgeConfigSpec.Builder().configure(ClientConfig::new);
    CLIENT = clientSpecPair.getLeft();
    CLIENT_SPEC = clientSpecPair.getRight();
  }
}
