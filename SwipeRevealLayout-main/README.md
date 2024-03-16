## SwipeRevealLayout
A layout that you can swipe/slide to show another layout.
This is a fork project from https://github.com/chthai64/SwipeRevealLayout

### Demo
##### Overview
![Demo all](https://raw.githubusercontent.com/raquezha/SwipeRevealLayout/main/art/demo_all.gif)

##### Drag mode

Drag mode normal:   
![Demo normal](https://raw.githubusercontent.com/raquezha/SwipeRevealLayout/main/art/demo_normal.gif)

Drag mode same_level:   
![Demo same](https://raw.githubusercontent.com/raquezha/SwipeRevealLayout/main/art/demo_same.gif)

### Features
* Written in kotlin
* Flexible, easy to use with RecyclerView, ListView or any view that requires view binding.
* Four drag edges (left, right, top, bottom).
* Two drag modes:
    * Normal (the secondary view is underneath the main view).
    * Same level (the secondary view sticks to the edge of the main view).
* Able to open one row at a time.
* Minimum api level 21.

### Usage
#### Dependencies
```groovy
dependencies {
    implementation 'WIP'
}
```

#### Layout file
```xml
<com.chauthai.swipereveallayout.SwipeRevealLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:mode="same_level"
        app:dragEdge="left">

        <!-- Your secondary layout here -->
        <FrameLayout
            android:layout_width="wrap_content"
            android:layout_height="match_parent" />

        <!-- Your main layout here -->
        <FrameLayout
            android:layout_width="match_parent"
            android:layout_height="match_parent" />
            
</com.chauthai.swipereveallayout.SwipeRevealLayout>
```
```app:mode``` can be ```normal``` or ```same_level```

```app:dragEdge``` can be ```left```, ```right```, ```top``` or ```bottom```
