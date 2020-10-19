## Reproducer

```bash
JDWP exit error JVMTI_ERROR_NONE(0): getting frame location [stepControl.c:641]
```

## Issue
Class reloading [issue](https://github.com/raphw/byte-buddy/issues/925) - using `byte-buddy` when debugging with `IntelliJ IDEA`

## Requirements

**Java**          - `1.8`                          
**IntelliJ IDEA** -`2020.2 ultimate edition`

## Get started
1. open `com.example.Main` class
2. set **debug** breakpoints - line `21` && `22`
3. execute `com.example.Main` in **debug** mode
      
_output_:
```bash
JDWP exit error JVMTI_ERROR_NONE(0): getting frame location [stepControl.c:641]
Unhandled exception
Type=Segmentation error vmState=0x00000000
```
