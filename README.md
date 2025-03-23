# RNSplash

RNSplash é um projeto React Native para gerenciar telas de splash com animações personalizáveis. Permite controlar com facilidade o tempo e a forma como a tela de splash desaparece em seu aplicativo.

## Recursos

- Diferentes tipos de animações para esconder a tela de splash (fade, scale, none)
- Configuração personalizada de duração e atraso

## Rodar Projeto

```bash
# using npm
npm start

# OR using Yarn
yarn start
```

## Codebase

### Javascript


### Caminho do NativeSplash.ts

```
RNSplash/NativeSplash.ts
```

### Import do NativeSplash.ts no App.tsx
```typescript
import { hideSplashScreen } from './NativeSplash';
```

### Escondendo a tela de splash com configurações padrão

```typescript
// Usará uma animação de escala com duração de 500ms e atraso de 200ms
hideSplashScreen();
```

### Personalizando a animação

```typescript
// Com tipo de animação personalizado
hideSplashScreen({ animationType: 'fade' });

// Com duração e atraso personalizados
hideSplashScreen({
  animationType: 'scale',
  duration: 800,    // duração em ms
  delay: 300        // atraso em ms
});
```

## Opções

| Propriedade    | Tipo                           | Padrão    | Descrição                           |
|----------------|--------------------------------|-----------|-------------------------------------|
| animationType  | 'none' \| 'fade' \| 'scale'    | 'scale'   | Tipo de animação                    |
| duration       | number                         | 500       | Duração da animação em ms           |
| delay          | number                         | 200       | Atraso antes da animação iniciar    |


### Android

#### Estrutura de Arquivos

Os arquivos relacionados ao módulo nativo da Splash estão localizados no seguinte diretório:

```
android/app/src/main/java/com/rnsplash/
```

Este diretório contém os seguintes arquivos importantes:
- `SplashModule.kt` - Implementação principal do módulo nativo
- `SplashPackage.kt` - Package que registra o módulo
- `RCTSplashScreen.kt` - Core da splash

#### Configuração da Imagem

A imagem da tela de splash deve:
- Ser nomeada como `splash.png`
- Estar localizada em: `android/app/src/main/res/drawable/`
- Preferencialmente ter diferentes resoluções para diversos tamanhos de tela (usando as pastas drawable-hdpi, drawable-xhdpi, etc.)

#### Dicas de Otimização

- Use imagens PNG otimizadas para reduzir o tamanho do APK
- Para uma experiência consistente, mantenha as proporções da imagem em todas as resoluções
- Teste a splash em diferentes dispositivos para garantir boa aparência em todos os tamanhos de tela

## Exemplo completo

```typescript
import React, { useEffect } from 'react';
import { View, Text } from 'react-native';
import { hideSplashScreen } from './NativeSplash';

function App() {
  useEffect(() => {
    // Esconde a tela de splash quando o componente for montado
    hideSplashScreen({
      animationType: 'fade',
      duration: 1000,
      delay: 500
    });
  }, []);

  return (
    <View>
      <Text>Meu App!</Text>
    </View>
  );
}

export default App;
```

## Demonstração

Abaixo está uma demonstração visual de como a tela de splash funciona:

![Demonstração da Splash Screen](./assets/splash-demo.gif)
