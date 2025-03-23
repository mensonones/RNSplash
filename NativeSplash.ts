import { NativeModules } from 'react-native';

const { NativeSplashModule } = NativeModules;

type AnimationTypeValue = 'none' | 'fade' | 'scale';

type SplashOptions = {
  animationType?: AnimationTypeValue;
  duration?: number;
  delay?: number;
};

const typeAnimation = (type: AnimationTypeValue): string => {
  if (!NativeSplashModule?.animationType) {
    console.warn('NativeSplashModule não disponível');
    return '';
  }

  const animationType = {
    none: NativeSplashModule.animationType.none,
    fade: NativeSplashModule.animationType.fade,
    scale: NativeSplashModule.animationType.scale,
  };

  return animationType[type];
};

/**
 * Esconde a tela de splash com animação configurável
 */
export const hideSplashScreen = (options: SplashOptions = {}): void => {
  if (!NativeSplashModule?.hide) {
    console.warn('NativeSplashModule.hide não disponível');
    return;
  }

  const {
    animationType = 'scale',
    duration = 500,
    delay = 200,
  } = options;

  NativeSplashModule.hide({
    animationType: typeAnimation(animationType),
    duration,
    delay,
  });
};