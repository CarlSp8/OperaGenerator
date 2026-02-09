# 🎭 Opera Generator - System Preview

## Overview

The **Opera Generator** is an advanced AI-powered system that creates complete multimedia operas by orchestrating multiple state-of-the-art AI models. Each opera is a unique collaboration between different AI systems, producing rich narratives with professional formatting, stunning visuals, and dramatic audio narration.

---

## 🎨 What Gets Generated

Every opera production includes:

### 📝 **Complete Libretto**
- Professionally formatted markdown document with proper operatic conventions
- **Automatic stanza formatting** - all character lyrics beautifully displayed with blockquotes
- Character voice types automatically assigned (soprano, tenor, baritone, bass)
- Stage directions clearly marked
- Embedded scene illustrations

### 🖼️ **AI-Generated Illustrations**
- One unique illustration per scene
- Created using Google's **Gemini Nano Banana** (gemini-3-pro-image-preview)
- High-resolution PNG images capturing the drama and setting
- Intelligent rate limiting to prevent API throttling

### 🎙️ **Voice Narration**
- **Opera Introduction** - Dramatic narration setting the stage
- **Scene Narrations** - AI-voiced stage directions using ElevenLabs
- Professional voice actors: Bella (narrator) and Antoni (critic)
- MP3 format, ready for playback or presentations

### 📰 **Critical Reviews**
- AI-generated opera critique using Google Gemini 3 Pro
- In-depth analysis of characters, themes, and dramatic structure
- Scholarly perspective on the work's artistic merit
- Audio version of the review available

### 📦 **Organized Output Structure**
```
production_runs/<timestamp>_<title>/
├── <title>_complete_libretto.md          # Full opera with embedded images
├── <title>_critique.md                    # AI critic's review
├── opera_introduction.mp3                 # Dramatic introduction
├── scene_1_<title>.txt                    # Individual scene files
├── scene_1_illustration.png               # Scene artwork
├── scene_1_narration.mp3                  # Stage direction audio
├── scene_2_<title>.txt
├── scene_2_illustration.png
├── scene_2_narration.mp3
├── production_metadata.json               # Run metadata
└── ...
```

---

## 🤖 The AI Ensemble

The system uses multiple AI models working in concert:

### Text Generation (Alternating)
- **GPT-5.2** (OpenAI) - Writes odd-numbered scenes with lyrical, evocative style
- **Claude Opus 4.5** (Anthropic) - Writes even-numbered scenes with precise, dramatic flair
- This alternation creates unique stylistic variety and narrative dynamics

### Visual Generation
- **Gemini Nano Banana** (Google) - Creates stunning scene illustrations
- Sophisticated prompt engineering for dramatic, operatic visuals
- Rate-limited concurrent generation (default: 2 simultaneous, 1-second delays)

### Critical Analysis
- **Gemini 3 Pro** (Google) - Generates scholarly opera critiques
- Analyzes character development, dramatic arc, and theatrical effectiveness

### Voice Synthesis
- **ElevenLabs API** - Professional text-to-speech narration
- Multiple voice personas for different characters
- Streams audio directly to MP3 files

---

## 🎪 Featured Opera: "Hartford Ascending"

### The Story

Set in post-climate change Connecticut where jungle has reclaimed civilization, this 8-scene opera follows:

- **Sandra** (soprano) - An intrepid explorer seeking the lost city of Hartford
- **Lucian** (tenor) - A jungle poet who falls in love with Sandra
- **Maximilian** (baritone) - A government agent trying to stop them
- **Aria-7 Robot** (bass) - Maximilian's AI that sings opera in three languages

### Scene 1: "Encounter Beneath the Banyan Trees"

![Scene 1 - Sandra in the Jungle](https://github.com/user-attachments/assets/b910315f-aeb6-4d98-a579-5f87fc133958)

*AI-generated illustration showing Sandra (soprano) discovering the jungle path to Hartford*

#### Excerpt from the Libretto

**Stage Direction:**
> [The stage is lush and green, tangled with vines and enormous leaves. Birds call from above, and shafts of golden sunlight pierce the green gloom. The explorer SANDRA (soprano) hacks through the undergrowth, sweat on her brow, map in hand.]

**SANDRA** (soprano):
> What verdant wall impedes my quest,<br>
> Each fragrant root, each echoing cry?<br>
> Oh, Hartford, cradle of dreams long lost,<br>
> Still you elude me: but I will not rest—<br>
> Until my compass spins no more,<br>
> Until your marble halls I spy!

**LUCIAN** (tenor):
> A human voice in this cathedral wild?<br>
> Not echo, not shadow, not bird—a soul!<br>
> O muse, what brings you to my emerald exile?<br>
> Did the flowers summon you, or the thunder roll?

*Written by GPT-5.2*

#### What Makes This Image Special

Notice how the AI-generated illustration perfectly captures:
- ✅ **Atmospheric Lighting** - Golden sunlight filtering through dense jungle canopy
- ✅ **Character Details** - Sandra with explorer's hat, machete, and weathered map
- ✅ **Dramatic Composition** - Subject positioned in golden spotlight, creating focus
- ✅ **Narrative Elements** - Every prop tells the story (map, machete, jungle path)
- ✅ **Operatic Drama** - Her expression conveys determination and wonder
- ✅ **Rich Color Palette** - Lush greens and warm golds evoke the setting

This is just Scene 1 - each scene gets its own unique, dramatically appropriate illustration!

### What the Critics Say

> *"'Hartford Ascending: An Opera of Love and Ruins' is a work of considerable promise. Its fearless embrace of the bizarre, combined with a clear understanding of operatic dramatic structure and character archetypes, sets it apart... It's a bold, imaginative vision that should excite anyone seeking a new kind of opera – one where love blossoms amidst overgrown highways, and government agents wield singing robots as weapons."*
> 
> — Google Gemini AI Critic

---

## 🎵 Another Featured Opera: "Vines of Hartford, Arias of Steel"

A second variation on the post-apocalyptic Hartford theme, featuring:

### Scene 1: "The Charter Oak Awakes"

```markdown
[The stage is a steaming green cathedral. Vines drape the ribs of 
a collapsed highway sign: I-84 EAST—HARTFORD. The dome of a distant 
ruined building glints blue beneath strangler figs.]

ELARA, SOPRANO:
O river that forgot your name in vines,
O avenue of maples swallowing the lanes—
I have followed steam and rumor, broken codes
in lichened masonry the ivy keeps.
Hartford, ghost of ledgers and blue domes,
let me find your heart before my own falls quiet.
```

This opera includes:
- ✅ Complete narrated introduction (MP3)
- ✅ Individual scene narrations for all scenes
- ✅ Stunning AI-generated illustrations
- ✅ NotebookLM podcast package integration
- ✅ Suno AI music prompts for actual opera composition

---

## 🚀 Technical Highlights

### Modern Java 21 Features
- **Virtual Threads** - Concurrent image generation without thread pool overhead
- **Records** - Clean domain modeling (Opera, Scene)
- **Pattern Matching** - Elegant switch expressions
- **Text Blocks** - Multi-line prompt strings

### Performance
- Scene generation: ~30 seconds per scene
- Image generation: ~1 minute per scene (rate-limited)
- Voice narration: ~10-15 seconds per scene
- **Total time for 5-scene opera**: ~8-10 minutes

### Intelligent Rate Limiting
Configurable via environment variables or system properties:
```bash
# Environment variables
export OPERA_IMAGE_MAX_CONCURRENT=4
export OPERA_IMAGE_DELAY_MS=500

# Or system properties
./gradlew run -Dopera.image.maxConcurrent=4 -Dopera.image.delayMs=500
```

---

## 📁 Assets Available

### In the Repository

The `showcase/` directory contains complete operas with all assets:

1. **Hartford Ascending: An Opera of Love and Ruins**
   - 8 complete scenes
   - All illustrations (PNG)
   - Audio introduction (MP3)
   - Complete libretto with formatting
   - Critical review
   - Scene music files (WAV/MP3)
   - NotebookLM podcast screenshot

2. **Iron Aria: The Lost City of Hartford**
   - 6 complete scenes
   - All scene illustrations

3. **Vines of Hartford, Arias of Steel**
   - Complete scenes with narration
   - Scene illustrations
   - NotebookLM podcast package
   - Suno AI music prompts
   - Critical critique

---

## 🎬 How It Works

### The 5-Step Generation Process

**Step 1: Text Generation**
- GPT-5.2 and Claude Opus 4.5 alternate writing scenes
- Each model brings unique stylistic elements
- Conversation class manages the back-and-forth

**Step 2: Automatic Formatting**
- LibrettoWriter applies professional opera formatting
- Character lines detected and converted to blockquotes
- Voice types intelligently assigned
- Stage directions preserved

**Step 3: Voice Narration** (Optional)
- NarratorVoice extracts stage directions
- ElevenLabs generates dramatic audio
- Introduction and scene-by-scene narration

**Step 4: Image Generation**
- GeminiImageGenerator creates illustrations
- Rate-limited concurrent requests
- Saves high-resolution PNG files

**Step 5: Critical Review** (Optional)
- OperaCritic generates scholarly review
- Audio version created for listening
- In-depth analysis of the work

---

## 🎯 Key Features

### ✨ **AI Collaboration**
Two different AI models alternate writing scenes, creating unique narrative dynamics and stylistic variety

### 🎨 **Visual Storytelling**
Every scene illustrated with AI-generated artwork that captures the drama and atmosphere

### 🎙️ **Dramatic Narration**
Professional voice synthesis brings stage directions to life with theatrical flair

### 🎵 **Audio Playback**
Built-in JLayer integration for live demonstrations and presentations

### 📝 **Automatic Formatting**
Beautiful stanza formatting applied automatically - no manual intervention needed

### 🔄 **Opera Continuation**
Incomplete operas can be continued with proper context and scene numbering

### 🛡️ **Rate Limiting**
Intelligent throttling prevents API rate limit issues during image generation

### 📊 **Production Tracking**
Metadata logging for all generation runs with timestamps and model information

---

## 🎓 Integration Capabilities

### External Services

**Suno AI** - Actual opera music composition
- Generate prompts from libretto
- Create full musical scores
- Professional audio production

**NotebookLM** - AI-generated podcasts
- Discusses the opera's themes and creation
- Behind-the-scenes AI collaboration story
- Educational content about the work

---

## 🔧 Quick Start

### Generate Your First Opera

```bash
# 1. Set up API keys
export OPENAI_API_KEY=your_key
export ANTHROPIC_API_KEY=your_key
export GOOGLE_API_KEY=your_key
export ELEVENLABS_API_KEY=your_key  # Optional

# 2. Build the project
./gradlew build

# 3. Generate an opera (auto-generated title, 5 scenes)
./gradlew run

# 4. Or specify custom parameters
java -cp build/classes/java/main com.kousenit.IntegratedOperaGenerator "My Opera Title" 7
```

### Generate Voice Narration

```bash
# Create and play dramatic narration
./gradlew test --tests AudioDemoTest::generateAndPlayOperaIntroduction
```

### Generate Critical Review

```bash
# Get an AI critic's perspective
./gradlew test --tests OperaCriticTest
```

---

## 📚 Available Documentation

- **`README.md`** - Comprehensive user guide
- **`CLAUDE.md`** - Technical context for development
- **`EXECUTION_GUIDE.md`** - Step-by-step execution instructions
- **`LIBRETTO_FORMATTING_GUIDE.md`** - Formatting system details
- **`TEST_TAGGING_GUIDE.md`** - Test organization guidelines
- **Slide deck** - "AI Opera Generator: Multiple LLMs in Concert" presentation

---

## 🎭 The Result

The Opera Generator demonstrates the power of multi-model AI collaboration in creative arts. Each opera is:

- ✅ **Narratively Coherent** - Complete dramatic arc from beginning to end
- ✅ **Visually Stunning** - Professional illustrations for every scene
- ✅ **Aurally Engaging** - Dramatic narration brings the work to life
- ✅ **Professionally Formatted** - Ready for reading or performance
- ✅ **Critically Reviewed** - Scholarly analysis included
- ✅ **Fully Organized** - All assets structured and accessible

---

## 🌟 Why It Matters

This project showcases:

1. **Multi-Model Collaboration** - Different AIs working together create richer results than any single model
2. **Complete Artistic Pipeline** - From conception to critique, fully automated
3. **Professional Quality** - Output ready for actual use in presentations or performances
4. **Modern Technology** - Leverages cutting-edge AI models and Java 21 features
5. **Extensible Architecture** - Easy to add new models, features, or output formats

---

## 🎬 See It In Action

The `showcase/` directory contains complete examples with all generated assets. Each opera demonstrates the system's full capabilities:

- Complete libretti with beautiful formatting
- Stunning scene illustrations
- Dramatic audio narrations
- Critical reviews
- Integration with external music and podcast services

**Ready to explore?** Browse the showcase directory or generate your own opera!

---

*Generated by the Opera Generator AI System*  
*"Where multiple AIs collaborate to create art"*
