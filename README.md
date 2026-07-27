# REPETITA: Repeatable Experiments for Performance Evaluation of Traffic-Engineering Algorithms

[![Build Status](https://img.shields.io/badge/build-passing-brightgreen.svg)](https://github.com/uclouvain/repetita)
[![Java Version](https://img.shields.io/badge/Java-17%2B-blue.svg)](https://www.oracle.com/java/)
[![Maven](https://img.shields.io/badge/Maven-3.8%2B-C71A36.svg)](https://maven.apache.org/)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](LICENSE)
[![arXiv](https://img.shields.io/badge/arXiv-1710.08665-B31B1B.svg)](https://arxiv.org/abs/1710.08665)

**REPETITA** is an open-source, extensible evaluation framework for Traffic Engineering (TE) algorithms. It provides a standardized environment to run, evaluate, compare, and benchmark routing optimization algorithms under identical conditions on real-world network topologies (such as Internet Topology Zoo and RocketFuel) and traffic demands.

For complete background, methodology, and experimental results, please read the accompanying paper:
> **REPETITA: Repeatable Experiments for Performance Evaluation of Traffic-Engineering Algorithms**  
> Steven Gay, Pierre Schaus, Stefano Vissicchio  
> [arXiv:1710.08665](https://arxiv.org/abs/1710.08665) [cs.NI].

---

## Key Features

- **Extensible Solver Suite**: Implements state-of-the-art algorithms across Constraint Programming (CP), Local Search (LS), Linear Programming / MIP, and IGP Weight Optimization (WO).
- **Extensive Datasets**: Built-in support for real-world topologies and traffic demand matrices (Topology Zoo, RocketFuel, DEFO benchmarks).
- **Multi-Technology Simulators**: Accurately simulates link utilization and forwarding paths for:
  - Equal-Cost Multi-Path (ECMP) routing
  - Segment Routing (SR) with node/adjacency segments
  - Static explicit paths (MPLS TE tunnels, OpenFlow rules)
  - Multi-Commodity Flow (MCF) theoretical lower bounds
- **Evaluation Scenarios**: Evaluate robustness and re-optimization speed under failure series and dynamic demand changes.
- **External Solvers Interface**: Effortlessly plug in custom external algorithms (written in Python, C++, Julia, or shell) via a simple CLI specification file.

---

## Solvers & Underlying Technologies

REPETITA integrates several powerful optimization engines:

### 1. Constraint Programming Engine: MaxiCP (`0.0.3`)
- **`DefoCP`** (`edu.repetita.solvers.sr.DefoCP`): Segment Routing path optimizer implementing the declarative CP approach described in [Hartert et al., SIGCOMM 2015](https://dl.acm.org/doi/pdf/10.1145/2829988.2787495). Powered by the lightweight, high-performance **[MaxiCP](https://org.maxicp)** Constraint Programming solver.

### 2. Mixed Integer Programming Engine: Google OR-Tools (`9.8`)
- **`MIPTwoSRNoSplit`** (`edu.repetita.solvers.sr.MIPTwoSRNoSplit`): Segment Routing optimizer utilizing 2-segment paths inspired by [Bhatia et al., INFOCOM 2015].
- **`MIPWeightOptimizer`** (`edu.repetita.solvers.wo.MIPWeightOptimizer`): MIP formulation for optimizing link weights.
- **`MCF`** (`edu.repetita.core.MCF`): Linear Program computing the Multi-Commodity Flow optimal lower bound for max link utilization.

### 3. Local Search & Metaheuristics
- **`SRLS`** (`edu.repetita.solvers.sr.SRLS`): Sub-second Reactive Local Search path optimizer for Segment Routing based on [Gay et al., INFOCOM 2017](https://link.springer.com/content/pdf/10.1007/978-3-319-23219-5_41.pdf).
- **`TabuIGPWO`** (`edu.repetita.solvers.wo.TabuLS`): Tabu search IGP weight optimization algorithm inspired by [Fortz & Thorup, INFOCOM 2000].

### 4. External Solvers Interface
- Interface allowing custom standalone executables (e.g., Python scripts in `external_solvers/`) to be executed as REPETITA solvers.

---

## Requirements & Prerequisites

- **Java JDK**: Version 17 or higher
- **Build Tool**: Apache Maven 3.8+
- **Python**: Python 3.x (optional, required only for running example external Python solvers)

---

## Installation & Build

Clone the repository and build using Maven:

```bash
git clone https://github.com/uclouvain/repetita.git
cd repetita

# Compile and package the project into a executable JAR
mvn clean package

# Run the test suite
mvn test
```

Upon successful build, the executable JAR is generated in `target/repetita-0.1.0.jar`.

---

## Usage & Quick Start

### Basic CLI Command

Run REPETITA using `java -jar target/repetita-0.1.0.jar` with desired parameters:

```bash
java -jar target/repetita-0.1.0.jar \
  -graph data/2016TopologyZooUCL_inverseCapacity/Airtel.graph \
  -demands data/2016TopologyZooUCL_inverseCapacity/Airtel.0000.demands \
  -solver defoCP \
  -scenario SingleSolverRun \
  -t 10 \
  -verbose 1
```

### CLI Command Options

| Parameter | Mandatory | Description | Example Values |
|---|---|---|---|
| `-graph` | **Yes** | Path to input topology graph file | `data/.../Airtel.graph` |
| `-demands` | **Yes** | Path to input demands file | `data/.../Airtel.0000.demands` |
| `-solver` | **Yes** | Identifier of the solver algorithm to execute | `defoCP`, `SRLS`, `TabuIGPWO`, `MIPTwoSRNoSplit`, `IGP` |
| `-scenario` | **Yes** | Identifier of the scenario evaluation | `SingleSolverRun`, `SingleLinkFailureRobustness`, `SingleLinkFailureReoptimization`, `DemandChangeReoptimization` |
| `-t` | No | Maximum time limit for the solver (in seconds) | `5`, `10.5` |
| `-out` | No | Output file for logging scenario metrics | `results.out` |
| `-outpaths` | No | Output file for dumping computed routing paths | `paths.txt` |
| `-verbose` | No | Debugging verbosity level (`0` = silent, `1` = standard, `2` = verbose) | `1` |

---

## Built-in Solvers & Scenarios

### Supported Solvers

- **`defoCP`**: Segment Routing CP optimizer ([Hartert et al., SIGCOMM 2015](https://dl.acm.org/doi/pdf/10.1145/2829988.2787495)).
- **`SRLS`**: Sub-second Segment Routing local search optimizer ([Gay et al., INFOCOM 2017](https://link.springer.com/content/pdf/10.1007/978-3-319-23219-5_41.pdf)).
- **`MIPTwoSRNoSplit`**: MIP 2-segment routing optimizer ([Bhatia et al., INFOCOM 2015]).
- **`MIPWeightOptimizer`**: MIP IGP weight optimizer.
- **`TabuIGPWO`**: Tabu search IGP weight optimizer ([Fortz & Thorup, INFOCOM 2000]).
- **`IGP`**: Baseline IGP shortest-path routing (ECMP).
- **`randomExplicitPaths` / `randomLinkWeights`**: Sample external solvers specified in `external_solvers/solvers-specs.txt`.

### Evaluation Scenarios

- **`SingleSolverRun`**: Runs the configured solver on the topology and traffic matrix, reporting post-optimization max link utilization, solve time, and detour statistics.
- **`SingleLinkFailureRobustness`**: Evaluates how link failures impact maximum link utilization under the routing paths computed by the solver.
- **`SingleLinkFailureReoptimization`**: Re-optimizes traffic distribution upon consecutive link failures.
- **`DemandChangeReoptimization`**: Simulates dynamic demand matrix changes and re-optimizes routing paths across demand shifts.

---

## Code Architecture

The codebase is structured under `edu.repetita`:

```
edu.repetita
├── core                   # Core data structures (Topology, Demands, Setting, MCF)
├── simulators             # Flow simulators (FlowSimulator, SegmentRoutingFlowSimulator, ExplicitPathFlowSimulator)
├── solvers                # Base solver interfaces & implementations
│   ├── sr                 # Segment Routing solvers
│   │   ├── DefoCP.java    # DefoCP wrapper
│   │   ├── SRLS.java      # SRLS wrapper
│   │   ├── defo           # MaxiCP-based DEFO CP engine
│   │   └── rls            # Reactive Local Search engine
│   └── wo                 # Weight Optimization solvers (TabuLS, MIPWeightOptimizer)
├── scenarios              # Evaluation scenarios
├── io                     # Parsers and output writers
├── analyses               # Performance analysis modules
└── main                   # CLI Main entrypoint
```

---

## Citations & Academic References

If you use REPETITA in your research, please cite the REPETITA paper and the relevant underlying solver papers:

### REPETITA Benchmark Framework
```bibtex
@article{gay2017repetita,
  title={REPETITA: Repeatable Experiments for Performance Evaluation of Traffic-Engineering Algorithms},
  author={Gay, Steven and Schaus, Pierre and Vissicchio, Stefano},
  journal={arXiv preprint arXiv:1710.08665},
  year={2017}
}
```

### DEFO Constraint Programming Solver
```bibtex
@inproceedings{hartert2015declarative,
  title={A Declarative and Expressive Approach to Control Forwarding Paths in Carrier-Grade Networks},
  author={Hartert, Renaud and Vissicchio, Stefano and Schaus, Pierre and Bonaventure, Olivier and Filsfils, Clarence and Telkamp, Thomas and Francois, Pierre},
  booktitle={ACM SIGCOMM Computer Communication Review},
  volume={45},
  number={4},
  pages={15--28},
  year={2015}
}
```

### SRLS Sub-Second Local Search Solver
```bibtex
@inproceedings{gay2017expect,
  title={Expect the Unexpected: Sub-Second Optimization for Segment Routing},
  author={Gay, Steven and Hartert, Renaud and Vissicchio, Stefano},
  booktitle={IEEE INFOCOM 2017 - IEEE Conference on Computer Communications},
  pages={1--9},
  year={2017}
}
```

---

## License

REPETITA is licensed under the [Apache License 2.0](LICENSE).
