package pl.kodujdlapolski.ztm.core

import com.escalatesoft.subcut.inject.NewBindingModule
import pl.kodujdlapolski.ztm.config.CoreConfig


object CoreModule extends NewBindingModule(module => {
  import module._

  bind[CoreConfig] toSingle CoreConfig

})
